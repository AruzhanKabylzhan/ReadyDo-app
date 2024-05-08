package kz.rdd.core.ui.screen.zoomable_image

import android.net.Uri
import android.os.Parcelable
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.zoom.rememberZoomState
import com.smarttoolfactory.zoom.zoom
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.DialogDestination
import kz.rdd.core.ui.screen.loading_indicator.LoadingIndicatorDialogDestination
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CommonAsyncImage
import kz.rdd.core.ui.widgets.LoadingProgress

fun getImageDestination(
    behavior: ZoomableImageDialogDestination.Behavior,
    useLoadingProgressIndicator: Boolean = true,
): DialogDestination {
    if (useLoadingProgressIndicator && behavior is ZoomableImageDialogDestination.Behavior.UrlOpener) {
        val indicatorBehavior = LoadingIndicatorUrlBehavior(behavior.url)
        return LoadingIndicatorDialogDestination(
            behavior = indicatorBehavior,
        )
    }
    return ZoomableImageDialogDestination(
        behavior = behavior,
    )
}

@Parcelize
data class ZoomableImageDialogDestination(
    private val behavior: Behavior,
) : DialogDestination {

    sealed interface Behavior : Parcelable {
        @Parcelize
        data class UrlOpener(
            val url: String?,
        ) : Behavior {
            override val data: Any?
                get() = url
        }

        @Parcelize
        data class UriOpener(
            val uri: Uri
        ) : Behavior {
            override val data: Any
                get() = uri
        }

        val data: Any?
    }

    @Composable
    override fun Content(controller: DestinationController) {
        ZoomableImageContent(
            behavior = behavior,
            onClickBack = controller::navigateBack
        )
    }
}

@Composable
private fun ZoomableImageContent(
    behavior: ZoomableImageDialogDestination.Behavior,
    onClickBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = onClickBack,
                modifier = Modifier
                    .background(LocalAppTheme.colors.primaryText, shape = CircleShape)
                    .clip(shape = CircleShape),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_black_close),
                    contentDescription = "",
                    tint = LocalAppTheme.colors.white,
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
        ) {
            var isLoading by remember {
                mutableStateOf(true)
            }
            val showProgress = isLoading && behavior is ZoomableImageDialogDestination.Behavior.UrlOpener
            CommonAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .run {
                        heightIn(min = 200.dp, max = if (isLoading) 200.dp else Dp.Unspecified)
                    }
                    .zoom(
                        key = Unit,
                        clip = false,
                        zoomState = rememberZoomState(
                            limitPan = true,
                            zoomable = true,
                            pannable = true,
                            rotatable = false,
                        ),
                    )
                    .animateContentSize(),
                contentScale = ContentScale.Fit,
                url = behavior.data,
                onLoading = {
                    isLoading = true
                },
                onError = {
                    isLoading = true
                },
                onSuccess = {
                    isLoading = false
                }
            )

            LoadingProgress(
                modifier = Modifier
                    .align(Alignment.Center),
                isVisible = showProgress
            )
        }
    }
}