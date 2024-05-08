package kz.rdd.core.ui.ext

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kz.rdd.core.ui.R
import kz.rdd.core.utils.ext.getUri
import java.io.File
import java.io.FileOutputStream

fun Context.findActivity(): Activity = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> throw Exception("Activity Not Found")
}

fun Context.setScreenOrientation(orientation: Int) {
    val activity = this.findActivity()
    activity.requestedOrientation = orientation
    if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
        hideSystemUi()
    } else {
        showSystemUi()
    }
}

fun Context.hideSystemUi() {
    val activity = this.findActivity()
    val window = activity.window ?: return
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, window.decorView).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

fun Context.showSystemUi() {
    val activity = this.findActivity()
    val window = activity.window ?: return
    WindowCompat.setDecorFitsSystemWindows(window, true)
    WindowInsetsControllerCompat(
        window,
        window.decorView
    ).show(WindowInsetsCompat.Type.systemBars())
}

fun Context.shareImage(uri: Uri) {
    val share = Intent(Intent.ACTION_SEND)
    share.type = "image/*"
    share.putExtra(Intent.EXTRA_STREAM, uri)
    share.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    share.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
    startActivity(Intent.createChooser(share, "Share Image"))
}

fun Context.sharePdf(file: File) {
    val uri = file.getUri(this)
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "application/pdf"
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    startActivity(Intent.createChooser(intent, getString(R.string.common_share)))
}

fun Bitmap.toUri(
    context: Context,
    imageName: String = "image.jpg",
    transform: (FileOutputStream) -> Boolean = {
        compress(CompressFormat.JPEG, 90, it)
    },
): Uri {
    try {
        File(context.cacheDir, "images").deleteRecursively()
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val stream = FileOutputStream("$cachePath/$imageName")
        transform(stream)
        stream.close()
    } catch (ex: Exception) {
    }

    // SHARE
    val imagePath = File(context.cacheDir, "images")
    val newFile = File(imagePath, imageName)
    return newFile.getUri(context)
}

fun Context.shareImage(bitmap: Bitmap) {
    shareImage(bitmap.toUri(this))
}

fun String.openInBrowser(context: Context) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(this))
    browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(Intent.createChooser(browserIntent, null))
}

fun Context.checkPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}

fun Activity.shouldShowRationale(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
}

fun Activity.switchFullScreen(isFullScreen: Boolean) {
    val root = findViewById<View>(android.R.id.content)
    if (isFullScreen) {
        WindowInsetsControllerCompat(window, root).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else {
        WindowInsetsControllerCompat(window, root)
            .show(WindowInsetsCompat.Type.systemBars())
    }
}

fun Context.launchTelegramChannel(id: String) {
    val intent = try {
        try {
            packageManager.getPackageInfoCompat("org.telegram.messenger")//Check for Telegram Messenger App
        } catch (e: Exception) {
            packageManager.getPackageInfoCompat("org.thunderdog.challegram")//Check for Telegram X App
        }
        Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=$id"))
    } catch (e: Exception) { //App not found open in browser
        Intent(Intent.ACTION_VIEW, Uri.parse("http://www.telegram.me/$id"))
    }
    startActivity(intent)
}

fun Context.launchIntent(url: String) {
    val https = "https://"
    val fullUrl = if (url.startsWith(https)) url else https + url
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fullUrl))
    startActivity(intent)
}

fun PackageManager.getPackageInfoCompat(packageName: String, flags: Int = 0): PackageInfo =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(flags.toLong()))
    } else {
        @Suppress("DEPRECATION") getPackageInfo(packageName, flags)
    }

fun Context.openSettings() {
    startActivity(getGoToSettingsIntent())
}

fun Context.getGoToSettingsIntent(): Intent {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.parse("package:$packageName")
    return intent
}

fun Context.openDeeplink(url: String): Intent {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.`package` = packageName
    return intent
}

fun Context.isGeoEnabled(context: Context): Boolean {
    val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isGPSEnabled: Boolean =
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val isNetworkEnabled: Boolean =
        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    return isGPSEnabled && isNetworkEnabled
}

fun Context.shareText(text: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}

fun Context.copyText(text: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(
        ClipData.newPlainText(
            "text",
            text
        )
    )
}