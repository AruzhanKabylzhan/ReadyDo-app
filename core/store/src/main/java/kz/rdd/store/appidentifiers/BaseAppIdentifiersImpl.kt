package kz.rdd.store.appidentifiers

import kz.rdd.core.store.BuildConfig

val isDebugBuild: Boolean get() = BuildConfig.DEBUG

internal abstract class BaseAppIdentifiersImpl constructor(
    private val appIdentifiersStorage: AppIdentifiersStorage,
    private val mockAppIdentifiersStorage: MockAppIdentifiersStorage
) : AppIdentifiers {

    override val deviceId: String
        get() {
            val realDeviceId = fetchDeviceId()
            return if (isDebugBuild) {
                mockAppIdentifiersStorage.deviceId ?: realDeviceId
            } else {
                realDeviceId
            }
        }
    override val deviceName: String
        get() {
            val realDeviceName = fetchDeviceName()
            return if (isDebugBuild) {
                mockAppIdentifiersStorage.deviceName ?: realDeviceName
            } else {
                realDeviceName
            }
        }
    override val sessionId: String
        get() {
            val realSessionId = fetchSessionId()
            return if (isDebugBuild) {
                mockAppIdentifiersStorage.sessionId ?: realSessionId
            } else {
                realSessionId
            }
        }
    override val platform: String
        get() = if (isDebugBuild) {
            mockAppIdentifiersStorage.platform ?: realPlatformName
        } else {
            realPlatformName
        }
    override val appVersionName: String
        get() = if (isDebugBuild) {
            mockAppIdentifiersStorage.appVersionName ?: realAppVersionName
        } else {
            realAppVersionName
        }

    protected abstract val realPlatformName: String

    protected abstract val realAppVersionName: String

    fun resetSessionId() {
        appIdentifiersStorage.sessionId = null
    }

    protected fun fetchDeviceId(): String {
        val deviceId = appIdentifiersStorage.deviceId
        return if (deviceId.isNullOrBlank()) {
            generateDeviceId().also { appIdentifiersStorage.deviceId = it }
        } else {
            deviceId
        }
    }

    protected fun fetchDeviceName(): String {
        val deviceName = appIdentifiersStorage.deviceName
        return if (deviceName.isNullOrBlank()) {
            generateDeviceName().also { appIdentifiersStorage.deviceName = it }
        } else {
            deviceName
        }
    }

    protected abstract fun generateDeviceId(): String

    protected abstract fun generateDeviceName(): String

    protected abstract fun generateRandomUUID(): String

    protected fun fetchSessionId(): String {
        val sessionId = appIdentifiersStorage.sessionId
        return if (sessionId.isNullOrBlank()) {
            generateRandomSessionId().also { appIdentifiersStorage.sessionId = it }
        } else {
            sessionId
        }
    }

    private fun generateRandomSessionId(): String {
        return generateRandomUUID()
    }
}
