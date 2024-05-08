package kz.rdd.store.appidentifiers.impl

import android.content.Context
import kz.rdd.core.utils.StringPreference
import kz.rdd.core.utils.getEncryptedSharedPreferences
import kz.rdd.store.appidentifiers.AppIdentifiersStorage

internal class AndroidAppIdentifiersStorageImpl(
    context: Context,
) : AppIdentifiersStorage {

    // it's required to use encrypted prefs or it will be non capable with previous realization
    private val prefs = context.getEncryptedSharedPreferences(
        prefsName = "app_prefs",
    )

    override var deviceId: String? by StringPreference(prefs)
    override var deviceName: String? by StringPreference(prefs)
    override var sessionId: String? by StringPreference(prefs)
}
