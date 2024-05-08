package kz.rdd.core.local_storage.data

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kz.rdd.core.local_storage.R
import kz.rdd.core.local_storage.data.dto.GalleryDataDto
import kz.rdd.core.local_storage.data.dto.StorageFileType
import kz.rdd.core.local_storage.domain.DownloadFileProgressListener
import kz.rdd.core.local_storage.domain.LocalStorage
import kz.rdd.core.utils.ext.getOriginalFileName
import kz.rdd.core.utils.ext.getUri
import kz.rdd.core.utils.ext.isImagePath
import kz.rdd.core.utils.ext.isVideoPath
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.io.path.extension

internal class LocalStorageImpl(private val context: Context) : LocalStorage {

    companion object {
        private const val DEFAULT_DATE_PATTERN = "yyyyMMdd_HHmmss"
    }

    private val mutex = Mutex()

    private val timeStamp: String
        get() = SimpleDateFormat(DEFAULT_DATE_PATTERN, Locale.getDefault())
            .format(Date())

    override suspend fun saveDownloadedFile(
        input: InputStream,
        url: String,
        fileName: String?
    ): Pair<String, Uri> {
        val uri = Uri.parse(url)

        val file = (fileName ?: uri.lastPathSegment)?.let {
            makeFileTemp(StorageFileType.UNKNOWN, it)
        } ?: makeFileTemp(StorageFileType.fromUriOrUnknown(uri))

        input.use {
            FileOutputStream(file).use { output ->
                it.write(output)
            }
        }

        return file.path to file.getUri(context)
    }

    override suspend fun downloadFromUrl(
        url: String,
        storageFileType: StorageFileType,
        progressListener: DownloadFileProgressListener,
        fileName: String?
    ): Pair<String, Uri>? {
        val file = getUrlFile(
            url = url,
            storageFileType = storageFileType,
            listener = progressListener,
            fileName = fileName
        )
        return file?.let {
            file.path to file.getUri(context)
        }
    }

    override suspend fun getImages(): List<GalleryDataDto> {
        val images: MutableList<GalleryDataDto> = mutableListOf()

        val imagesProjection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.TITLE
        )
        val externalContentUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val imagesCursor = context.contentResolver.query(
            externalContentUri,
            imagesProjection,
            null,
            null,
            null
        )

        imagesCursor.use {
            imagesCursor?.takeIf { it.count > 0 && imagesCursor.moveToFirst() }?.let { cursor ->
                val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val bucketColumn =
                    cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
                val dateAddedColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED)
                val titleColumn = cursor.getColumnIndex(MediaStore.Images.Media.TITLE)

                do {
                    val id = cursor.getString(idColumn).toLongOrNull() ?: 0
                    val uri = ContentUris.withAppendedId(externalContentUri, id)
                    val galleryData = GalleryDataDto(
                        id,
                        cursor.getString(titleColumn),
                        cursor.getString(bucketColumn),
                        uri.toString(),
                        MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE,
                        0,
                        cursor.getString(dateAddedColumn)
                    )
                    images.add(galleryData)
                } while (cursor.moveToNext())
            }
        }
        return images
    }

    override suspend fun getVideos(): List<GalleryDataDto> {
        val videos: MutableList<GalleryDataDto> = mutableListOf()

        val videosProjection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.DURATION
        )
        val externalContentUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        }
        val videosCursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            videosProjection,
            null,
            null,
            null
        )

        videosCursor.use {
            videosCursor?.takeIf { it.count > 0 && videosCursor.moveToFirst() }?.let { cursor ->
                val idColumn = cursor.getColumnIndex(MediaStore.Video.Media._ID)
                val bucketColumn = cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
                val dateAddedColumn = cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED)
                val titleColumn = cursor.getColumnIndex(MediaStore.Video.Media.TITLE)
                val durationColumn = cursor.getColumnIndex(MediaStore.Video.Media.DURATION)

                do {
                    val id = cursor.getString(idColumn).toLongOrNull() ?: 0
                    val uri = ContentUris.withAppendedId(externalContentUri, id)
                    val galleryData = GalleryDataDto(
                        cursor.getString(idColumn).toLongOrNull() ?: 0,
                        cursor.getString(titleColumn),
                        cursor.getString(bucketColumn),
                        uri.toString(),
                        MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO,
                        cursor.getLong(durationColumn),
                        cursor.getString(dateAddedColumn)
                    )
                    videos.add(galleryData)
                } while (cursor.moveToNext())
            }
        }
        return videos
    }

    override fun createFileTemp(fileType: StorageFileType, fileName: String?): Pair<String, Uri> {
        val file = makeFileTemp(fileType, fileName)
        return file.path to file.getUri(context)
    }

    override fun overwriteFile(inputPath: String, outputPath: String) {
        FileInputStream(File(inputPath)).use { input ->
            FileOutputStream(File(outputPath), false).use {
                input.write(it)
            }
        }
    }

    override suspend fun saveExternalFile(
        uri: Uri,
        fileName: String?,
        environment: String?
    ): Pair<String, Uri> {
        val file = makeFileTemp(
            StorageFileType.fromUriOrUnknown(uri),
            fileName ?: uri.getFileName() ?: uri.lastPathSegment,
            fileDirectory = environment?.let {
                Environment.getExternalStoragePublicDirectory(environment)
            } ?: context.cacheDir,
        )

        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(file).use {
                input.write(it)
            }
        }

        return file.path to file.getUri(context)
    }

    override fun getTempFile(storageFileType: StorageFileType, fileName: String?): File {
        return makeFileTemp(
            fileType = storageFileType,
            fileName = fileName,
        )
    }

    override fun getFileSize(path: String): Long = File(path).length()

    override fun clearFile(path: String) {
        PrintWriter(File(path)).run {
            print("")
            close()
        }
    }

    override fun getFilePair(path: String): Pair<String, Uri> {
        return path to File(path).getUri(context)
    }

    override fun getFileItemName(uri: Uri): String {
        val originalFileName = uri.getOriginalFileName(context) ?: ""
        val fileTypePrefix = getFileTypeName(originalFileName)
        val endOfFileName = "_${LocalDateTime.now()}" + "." + Paths.get(originalFileName).extension

        return context.getString(fileTypePrefix) + endOfFileName
    }

    private fun getFileTypeName(
        name: String
    ): Int {
        return when {
            Paths.get(name).extension.isImagePath() -> 0
            Paths.get(name).extension.isVideoPath() -> 0
            else -> 1
        }
    }

    private fun makeFileTemp(
        fileType: StorageFileType = StorageFileType.UNKNOWN,
        fileName: String? = null,
        fileDirectory: File? = context.cacheDir,
    ): File {
        val dir = fileDirectory.also { file ->
            file?.takeUnless { it.exists() }?.mkdirs()
        }
        return fileName?.let {
            File(dir, fileName)
        } ?: File.createTempFile(timeStamp, fileType.formatLikeSuffix, dir)
    }

    private fun Uri.getFileName(): String? = context.contentResolver
        .query(this, null, null, null, null)
        ?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            cursor.getString(nameIndex)
        }

    private fun InputStream.write(output: OutputStream) {
        val buffer = ByteArray(4 * 1024)
        var read: Int
        while (read(buffer).also { read = it } != -1) {
            output.write(buffer, 0, read)
        }
    }

    private suspend fun getUrlFile(
        url: String,
        storageFileType: StorageFileType,
        listener: DownloadFileProgressListener,
        fileName: String?,
    ): File? = withContext(Dispatchers.IO) {
        val (path, _) = createFileTemp(storageFileType, fileName)
        val temp = File(path)
        try {
            val url = URL(url)
            val connection = url.openConnection()
            connection.connect()
            mutex.withLock {
                streamToFile(
                    inputStream = BufferedInputStream(url.openStream()),
                    temp = temp,
                    bufferSize = 1024,
                    contentLength = getUrlContentLength(connection),
                    listener = listener
                )
            }
            temp
        } catch (e: IOException) {
            null
        }
    }

    private suspend fun streamToFile(
        inputStream: InputStream,
        temp: File,
        bufferSize: Int,
        contentLength: Long,
        listener: DownloadFileProgressListener,
    ) = withContext(Dispatchers.IO) {
        try {
            val fos: OutputStream = FileOutputStream(temp)
            val buffer = ByteArray(bufferSize)
            var read: Int
            var bytesWritten = 0
            var progress = 0f
            while (inputStream.read(buffer).also { read = it } != -1) {
                fos.write(buffer, 0, read)
                bytesWritten += read
                if (contentLength > 0) {
                    val currentProgress =
                        (bytesWritten.toFloat() / contentLength.toFloat())
                    if (currentProgress != progress) {
                        listener.onProgress(currentProgress)
                        progress = currentProgress
                    }
                }
            }
            fos.flush()
            fos.close()
        } catch (ignored: IOException) {
        } finally {
            try {
                inputStream.close()
            } catch (ignored: IOException) {
            } catch (ignored: NullPointerException) {
            }
        }
    }

    private fun getUrlContentLength(url: URLConnection): Long {
        try {
            val urlConnection = url as HttpURLConnection
            urlConnection.requestMethod = "HEAD"
            val lengthHeaderField = urlConnection.getHeaderField("content-length")
            val result = lengthHeaderField?.toLong()
            return if (result == null || result < 0L) -1L else result
        } catch (ignored: Exception) {
        }
        return -1L
    }

}
