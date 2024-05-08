package kz.rdd.core.ui.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.content.IntentCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.R
import kz.rdd.core.ui.ext.switchFullScreen
import kz.rdd.core.ui.screen.VideoPlayerActivity.Companion.PARAM_VIDEO

sealed interface VideoOpener : Parcelable {
    @Parcelize
    data class UrlOpener(
        val url: String?,
    ) : VideoOpener {
        override val data: Any?
            get() = url
    }

    @Parcelize
    data class UriOpener(
        val uri: Uri
    ) : VideoOpener {
        override val data: Any
            get() = uri
    }

    val data: Any?
}

class VideoPlayerActivity : ComponentActivity() {

    private lateinit var simpleExoPlayer: ExoPlayer

    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(this).inflate(R.layout.activity_video_player, null, false)
        playerView = view.findViewById(R.id.playerView)
        setContentView(view)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        switchFullScreen(true)
    }

    private fun initializePlayer() {

        val param = IntentCompat.getParcelableExtra(
            intent,
            PARAM_VIDEO,
            VideoOpener::class.java
        )

        val mediaDataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(this)

        val videoUri = Uri.parse(param?.data.toString())

        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoUri))

        val mediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)

        simpleExoPlayer = ExoPlayer.Builder(this)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()

        simpleExoPlayer.addMediaSource(mediaSource)

        simpleExoPlayer.playWhenReady = true
        playerView.player = simpleExoPlayer
        playerView.requestFocus()
    }

    private fun releasePlayer() {
        simpleExoPlayer.release()
    }

    public override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    public override fun onResume() {
        super.onResume()
        initializePlayer()
    }

    public override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    public override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    companion object {
        const val PARAM_VIDEO = "Video_opener_params"
    }
}

@UnstableApi
fun Context.getVideoIntent(behavior: VideoOpener): Intent {
    return Intent(
        this,
        VideoPlayerActivity::class.java
    ).apply {
        putExtra(PARAM_VIDEO, behavior)
    }
}