package kz.rdd.core.utils.ext

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun Uri.toMultipartBody(
    name: String = "file",
): MultipartBody.Part {
    val file = toFile()
    return MultipartBody.Part.createFormData(
        name = name,
        filename = file.name,
        body = file.asRequestBody("image/*".toMediaTypeOrNull())
    )
}

fun File.getUri(context: Context): Uri =
    FileProvider.getUriForFile(context, context.packageName + ".fileprovider", this)

fun Uri.getOriginalFileName(context: Context): String? {
    return context.contentResolver.query(this, null, null, null, null)?.use {
        val nameColumnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        it.moveToFirst()
        it.getString(nameColumnIndex)
    }
}

