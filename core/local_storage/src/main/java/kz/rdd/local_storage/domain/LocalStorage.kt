package kz.rdd.core.local_storage.domain

import android.net.Uri
import android.os.Environment
import kz.rdd.core.local_storage.data.dto.GalleryDataDto
import kz.rdd.core.local_storage.data.dto.StorageFileType
import java.io.File
import java.io.InputStream

interface LocalStorage {

    /**
     * Save downloaded file to local storage
     * [input] - file stream
     * [url] - link, use for file name and format
     * [fileName] - custom file name
     * @return file path and uri to share
     */
    suspend fun saveDownloadedFile(
        input: InputStream,
        url: String,
        fileName: String? = null
    ): Pair<String, Uri>

    /**
     * Download File to local storage
     * [url] - link, use for file name and format
     * [fileName] - custom file name
     */
    suspend fun downloadFromUrl(
        url: String,
        storageFileType: StorageFileType,
        progressListener: DownloadFileProgressListener = DownloadFileProgressListener { },
        fileName: String? = null,
    ): Pair<String, Uri>?

    /**
     * Get images from media store
     */
    suspend fun getImages(): List<GalleryDataDto>

    /**
     * Get videos from media store
     */
    suspend fun getVideos(): List<GalleryDataDto>

    /**
     * Create temporary file
     * [fileType] - type file
     * [fileName] - custom file name
     * @return file path and uri to share
     */
    fun createFileTemp(fileType: StorageFileType, fileName: String? = null): Pair<String, Uri>

    /**
     * Overwrite file
     * [inputPath] - source file
     * [outputPath] - destination file
     */
    fun overwriteFile(inputPath: String, outputPath: String)

    /**
     * Save file to local storage from external storage
     * @return file path and uri to share
     */
    suspend fun saveExternalFile(
        uri: Uri,
        fileName: String? = null,
        environment: String? = null,
    ): Pair<String, Uri>

    fun getTempFile(
        storageFileType: StorageFileType,
        fileName: String?
    ): File

    /**
     * Get file size in bytes
     * [path] - file path
     */
    fun getFileSize(path: String): Long

    /**
     * clear file content
     * [path] - file path
     */
    fun clearFile(path: String)

    /**
     * @return file path and uri to share
     */
    fun getFilePair(path: String): Pair<String, Uri>

    fun getFileItemName(uri: Uri): String
}
