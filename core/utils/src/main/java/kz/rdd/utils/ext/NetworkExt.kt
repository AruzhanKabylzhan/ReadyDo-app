package kz.rdd.core.utils.ext

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun MultipartBody.Builder.addFormDataPartWithSkippingNull(
    name: String,
    value: String?
): MultipartBody.Builder {
    return if (value != null) addFormDataPart(name, value) else this
}

fun MultipartBody.Builder.addFormDataPartWithSkippingNull(
    name: String,
    file: File?,
): MultipartBody.Builder {
    return if (file != null) addFormDataPart(
        name,
        file.name,
        file.asRequestBody("image/*".toMediaTypeOrNull())
    ) else this
}