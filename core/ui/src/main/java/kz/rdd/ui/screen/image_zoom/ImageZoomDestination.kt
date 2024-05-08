package kz.rdd.core.ui.screen.image_zoom

import android.net.Uri
import android.os.Parcelable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.LocalDestinationController
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.DialogDestination
import kz.rdd.core.ui.ext.clickableWithoutRipple
import kz.rdd.core.ui.widgets.CommonAsyncImage

@Parcelize
class ImageZoomDestination(
    private val resource: Resource?,
) : DialogDestination {

    override val blurRadius: Dp get() = 10.dp

    sealed interface Resource : Parcelable {
        val data: Any

        @Parcelize
        data class Url(val url: String) : Resource {
            override val data get() = url
        }

        @Parcelize
        data class Uri(val uri: android.net.Uri) : Resource {
            override val data get() = uri
        }
    }

    @Composable
    override fun Content(controller: DestinationController) {
        ImageContent(
            controller = controller,
            resource = resource,
        )
    }

    @Composable
    override fun OpenAnimation(controller: DestinationController) {
        Content(controller)
    }
}

fun Modifier.clickOpenFullImageContent(data: Any?) = clickOpenFullImageContent(
    when(data) {
        is Uri -> ImageZoomDestination.Resource.Uri(data)
        is String -> ImageZoomDestination.Resource.Url(data)
        else -> null
    }
)

fun Modifier.clickOpenFullImageContent(resource: ImageZoomDestination.Resource?) = composed {
    val controller = LocalDestinationController
    clickableWithoutRipple {
        openAsFullImageContent(
            controller = controller,
            resource = resource
        )
    }
}

fun openAsFullImageContent(
    controller: DestinationController,
    resource: ImageZoomDestination.Resource?,
) {
    controller.showDialog(ImageZoomDestination(resource))
}

@Composable
private fun ImageContent(
    controller: DestinationController,
    resource: ImageZoomDestination.Resource?,
) {
    Box(
        modifier = Modifier
            .padding(36.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickableWithoutRipple(
                onClick = controller::navigateBack
            )
    ) {
        CommonAsyncImage(
            url = resource?.data,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape)
                .clickableWithoutRipple {  },
            placeholder = painterResource(id = R.drawable.ic_user_photo_solo),
            error = painterResource(id = R.drawable.ic_user_photo_solo),
        )
    }
}
