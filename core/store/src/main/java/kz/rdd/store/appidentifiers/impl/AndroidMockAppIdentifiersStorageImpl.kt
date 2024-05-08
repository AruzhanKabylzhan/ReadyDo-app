package kz.rdd.store.appidentifiers.impl

import android.annotation.SuppressLint
import android.content.Context
import kz.rdd.core.utils.StringPreference
import kz.rdd.store.appidentifiers.MockAppIdentifiersStorage

internal class AndroidMockAppIdentifiersStorageImpl(
    context: Context
) : MockAppIdentifiersStorage {

    private val prefs = context.getSharedPreferences(
        "mock_headers_prefs",
        Context.MODE_PRIVATE
    )

    override var deviceId: String? by StringPreference(prefs)
    override var deviceName: String? by StringPreference(prefs)
    override var sessionId: String? by StringPreference(prefs)

    override var platform: String? by StringPreference(prefs)
    override var appVersionName: String? by StringPreference(prefs)

    @SuppressLint("ApplySharedPref")
    override fun clearAll() {
        prefs.edit().clear().commit()
    }
}
