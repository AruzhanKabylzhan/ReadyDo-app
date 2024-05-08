package kz.rdd.core.utils.ext

import android.text.TextUtils
import android.webkit.MimeTypeMap
import androidx.compose.ui.graphics.Color
import java.util.*

val imageExt = setOf(
    "jpeg",
    "png",
    "jpg",
    "webp",
)

val videoExt = setOf(
    "mp4",
    "mpeg",
    "heic",
    "heiv",
    "webm",
    "avi",
    "gif",
    "wmv",
    "mov",
)

fun String.capitalizeWithLocale() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}

fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.withAtSign() = if (startsWith('@')) this else "@$this"

fun String.onlyDigits() = filter {
    it.isDigit()
}

fun String.getMimeType(): String? {
    var type: String? = null
    val extension = MimeTypeMap.getFileExtensionFromUrl(this)
    if (extension != null) {
        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }
    return type
}

fun String.isImagePath(): Boolean {
    val extension = split(".").lastOrNull()
    return extension in imageExt
}

fun String.isVideoPath(): Boolean {
    val extension = split(".").lastOrNull()
    return extension in videoExt
}

fun String.isPdf(): Boolean {
    val extension = split(".").lastOrNull()
    return extension == "pdf"
}

fun String.getColor(): Color {
    return Color(android.graphics.Color.parseColor("#FF" + this))
}

val String.withBearer get() = "Bearer $this"
