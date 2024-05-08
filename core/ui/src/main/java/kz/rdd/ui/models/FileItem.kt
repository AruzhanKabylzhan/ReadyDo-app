package kz.rdd.core.ui.models

import android.net.Uri
import kz.rdd.core.utils.ext.FileType
import kz.rdd.core.utils.ext.fileType
import kz.rdd.core.utils.ext.lazyFast

sealed class FileItem {
    sealed interface FileRes {
        data class UriRes(val uri: Uri) : FileRes
        data class UrlRes(val url: String) : FileRes
    }
    abstract val fileName: String
    abstract val res: FileRes

    open val fileType by lazyFast {
        fileName.fileType
    }

    data class Loaded(
        val id: Int,
        override val fileName: String,
        override val res: FileRes,
        override val fileType: FileType = fileName.fileType
    ) : FileItem()

    data class Loading(
        override val fileName: String,
        val uri: Uri,
    ) : FileItem() {
        override val res: FileRes = FileRes.UriRes(uri)
    }
}
