package kz.rdd.core.local_storage.domain

fun interface DownloadFileProgressListener {
    fun onProgress(progress: Float)
}
