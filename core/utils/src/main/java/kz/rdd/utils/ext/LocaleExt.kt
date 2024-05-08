package kz.rdd.core.utils.ext

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import java.util.Locale

fun Activity.setLocale(languageCode: String) {
    setLocaleInternal(languageCode, this)
}

fun Service.setLocale(languageCode: String) {
    setLocaleInternal(languageCode, this)
}

fun String.getLocaleForCode(): Locale {
    val locale = Locale.forLanguageTag(this)
    if (locale.language == "en") {
        return Locale.UK
    }
    return locale
}

private fun setLocaleInternal(languageCode: String, context: Context) {
    val locale = languageCode.getLocaleForCode()
    Locale.setDefault(locale)
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        val resources: Resources = context.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}

fun Activity.checkIsNeedLocalizing(languageCode: String): Boolean {
    return getCurrentLocale() != Locale.forLanguageTag(languageCode) && Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
}

fun Fragment.getLocalizedResources(desiredLocale: Locale): Resources {
    var conf: Configuration = resources.configuration
    conf = Configuration(conf)
    conf.setLocale(desiredLocale)
    val localizedContext: Context = requireContext().createConfigurationContext(conf)
    return localizedContext.resources
}

fun Context.getCurrentLocale(): Locale {
    val locale = this.resources.configuration.locales.get(0) ?: defaultLanguage
    return Locale.forLanguageTag(locale.language)
}

@Composable
@ReadOnlyComposable
fun getLocale(): Locale {
    val configuration = LocalConfiguration.current
    val locale = ConfigurationCompat.getLocales(configuration).get(0)
        ?: LocaleListCompat.getDefault()[0]!!
    return Locale.forLanguageTag(locale.language)
}
