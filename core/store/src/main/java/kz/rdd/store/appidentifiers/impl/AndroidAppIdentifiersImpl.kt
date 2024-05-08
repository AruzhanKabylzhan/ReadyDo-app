package kz.rdd.store.appidentifiers.impl

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import kz.rdd.core.store.BuildConfig
import kz.rdd.core.utils.ext.capitalizeWithLocale
import kz.rdd.store.appidentifiers.AppIdentifiers
import kz.rdd.store.appidentifiers.AppIdentifiersStorage
import kz.rdd.store.appidentifiers.BaseAppIdentifiersImpl
import kz.rdd.store.appidentifiers.MockAppIdentifiersStorage
import java.util.UUID


internal class AndroidAppIdentifiersImpl constructor(
    private val context: Context,
    appIdentifiersStorage: AppIdentifiersStorage,
    mockAppIdentifiersStorage: MockAppIdentifiersStorage
) : BaseAppIdentifiersImpl(appIdentifiersStorage, mockAppIdentifiersStorage), AppIdentifiers {

    override val realPlatformName = "android"
    override val realAppVersionName = BuildConfig.VERSION_NAME

    init {
        fetchDeviceId()
        fetchSessionId()
    }

    @SuppressLint("HardwareIds")
    override fun generateDeviceId(): String {
        val androidId: String = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        ) ?: return generateRandomDeviceId()

        return if (checkAndroidIdValid(androidId)) {
            androidId
        } else {
            generateRandomDeviceId()
        }
    }

    override fun generateDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            model.capitalizeWithLocale()
        } else manufacturer.capitalizeWithLocale() + " " + model
    }

    override fun generateRandomUUID(): String {
        return UUID.randomUUID().toString()
    }

    private fun checkAndroidIdValid(androidId: String): Boolean {
        return androidId.filter { it != '0' }
            .trim()
            .isNotEmpty()
    }

    private fun generateRandomDeviceId(): String {
        return generateRandomUUID()
    }
}
