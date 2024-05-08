package kz.rdd.core.ui.ext

import android.content.Intent
import android.net.Uri
import kz.rdd.core.ui.base.navigation.ContextAction
import kz.rdd.core.ui.base.navigation.NavigationEvent
import kz.rdd.core.ui.base.navigation.ShowDialog
import kz.rdd.core.ui.base.navigation.StartActivity
import kz.rdd.core.ui.screen.VideoOpener
import kz.rdd.core.ui.screen.getVideoIntent
import kz.rdd.core.ui.screen.zoomable_image.ZoomableImageDialogDestination
import kz.rdd.core.ui.screen.zoomable_image.getImageDestination
import kz.rdd.core.utils.ext.FileType

fun FileType.getNavigationEvent(url: String): NavigationEvent {
    return when (this) {
        FileType.IMAGE -> {
            ShowDialog(
                getImageDestination(
                    ZoomableImageDialogDestination.Behavior.UrlOpener(url)
                )
            )
        }

        else -> {
            StartActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }
}

fun FileType.getNavigationEvent(uri: Uri): NavigationEvent {
    return when (this) {
        FileType.IMAGE -> {
            ShowDialog(
                getImageDestination(
                    ZoomableImageDialogDestination.Behavior.UriOpener(uri)
                )
            )
        }

        else -> {
            StartActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }
}
