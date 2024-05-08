package kz.rdd.core.utils

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Locale

@Parcelize
enum class Language(
    val code: String,
) : Parcelable {
    @SerializedName("English")
    English(code = "en");

    companion object
}

fun Language.Companion.fromLocale(locale: Locale) = when (locale.toLanguageTag().lowercase()) {
    Language.English.code -> Language.English
    else -> Language.English
}
