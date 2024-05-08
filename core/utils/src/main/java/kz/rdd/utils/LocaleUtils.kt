package kz.rdd.core.utils

import android.app.Activity
import android.app.LocaleManager
import android.os.Build
import android.os.LocaleList
import kz.rdd.core.utils.ext.getLocaleForCode
import kz.rdd.core.utils.ext.setLocale
import java.util.Locale

object LocaleUtil {

    fun changeApplicationLocaleOnRuntime(activity: Activity, languageCode: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val localeManager = activity.getSystemService(LocaleManager::class.java)
            localeManager.applicationLocales = LocaleList(languageCode.getLocaleForCode())
        } else {
            activity.setLocale(languageCode = languageCode)
            activity.recreate()
        }
    }
}
