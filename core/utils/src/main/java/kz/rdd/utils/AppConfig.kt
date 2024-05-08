package kz.rdd.core.utils

import android.content.SharedPreferences

class AppConfig(
    private val sharedPreferences: SharedPreferences,
) {
    private val defaultServerConfiguration = if (BuildConfig.DEBUG) {
        ServerConfiguration.DEV
    } else {
        ServerConfiguration.PROD
    }

    private val currentServerConfiguration: ServerConfiguration
        get() = if (BuildConfig.DEBUG) {
            currentServerConfigurationForDebug
        } else {
            defaultServerConfiguration
        }

    private var currentMemoryServerConfigurationForDebug: ServerConfiguration? = null

    var currentServerConfigurationForDebug: ServerConfiguration
        get() {
            val memory = currentMemoryServerConfigurationForDebug
            return if (memory != null) {
                memory
            } else {
                val ordinal =
                    sharedPreferences.getInt(SERVER_PREFS_PARAM, defaultServerConfiguration.ordinal)
                val prefValue = try {
                    ServerConfiguration.entries[ordinal]
                } catch (e: IndexOutOfBoundsException) {
                    defaultServerConfiguration
                }
                currentMemoryServerConfigurationForDebug = prefValue
                prefValue
            }
        }
        set(value) {
            sharedPreferences.edit().putInt(SERVER_PREFS_PARAM, value.ordinal).apply()
            currentMemoryServerConfigurationForDebug = value
        }

    val baseUrl get() = currentServerConfiguration.baseUrl

    private companion object {
        private const val SERVER_PREFS_PARAM = "serverConfParam"
    }
}