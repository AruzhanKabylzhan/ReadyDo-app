package kz.rdd.core.local_storage.data.dto

import android.net.Uri
import android.webkit.MimeTypeMap

enum class StorageFileType(
    val format: String,
) {
    IMAGE("jpg"),
    VIDEO("mp4"),
    PDF("pdf"),
    AUDIO("mp4"),
    AUDIO_M4A("m4a"),
    UNKNOWN("tmp");

    val formatLikeSuffix: String
        get() = ".$format"

    companion object {
        fun fromUri(uri: Uri): StorageFileType? = MimeTypeMap.getFileExtensionFromUrl(uri.path)
            ?.takeIf { it.isNotEmpty() }
            ?.let { format ->
                values().find { it.format == format }
            }

        fun fromUriOrUnknown(uri: Uri): StorageFileType = fromUri(uri) ?: UNKNOWN
    }
}