package kz.rdd.core.ui.widgets

import android.net.Uri
import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.DialogDestination
import kz.rdd.core.ui.screen.VideoOpener
import kz.rdd.core.ui.theme.LocalAppTheme

@Parcelize
data class VideoWindowDialogDestination(
    private val behavior: VideoOpener,
) : DialogDestination {

    @Composable
    override fun Content(controller: DestinationController) {
        VideoWindowContent(
            behavior = behavior,
            onClickBack = controller::navigateBack
        )
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun VideoWindowContent(
    behavior: VideoOpener,
    onClickBack: () -> Unit,
) {
    val context = LocalContext.current
    val videoUri = Uri.parse(behavior.data.toString())

    val isVideoLoading = remember {
        mutableStateOf(false)
    }


    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val defaultDataSourceFactory = DefaultDataSource.Factory(context)
                val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                    context,
                    defaultDataSourceFactory
                )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(videoUri))

                setMediaSource(source)

                playWhenReady = true
                repeatMode = Player.REPEAT_MODE_OFF

                addListener(object : Player.Listener {
                    override fun onIsLoadingChanged(isLoading: Boolean) {
                        isVideoLoading.value = isLoading
                    }
                })
                prepare()
            }
    }

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
            modifier = Modifier.fillMaxWidth(),
        ) {
            AndroidView(
                factory = {
                    PlayerView(context).apply {
                        hideController()
                        useController = true
                        player = exoPlayer
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            if (isVideoLoading.value) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.TopCenter),
                    trackColor = LocalAppTheme.colors.white,
                    color = LocalAppTheme.colors.main,
                )
            }
        }

        DisposableEffect(exoPlayer) {
            onDispose { exoPlayer.release() }
        }
    }
}