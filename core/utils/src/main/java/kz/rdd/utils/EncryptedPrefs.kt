package kz.rdd.core.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class EncryptedPrefs(
    private val context: Context,
    private val prefsName: String,
    private val legacyPrefsName: String? = null,
) : SharedPreferences {

    private val prefs: SharedPreferences by lazy {
        val encryptedPrefs = createEncryptedPrefs()
        if (legacyPrefsName != null && encryptedPrefs.all.isEmpty()) {
            val legacyPrefs = context.getSharedPreferences(legacyPrefsName, Context.MODE_PRIVATE)
            legacyPrefs.copyTo(encryptedPrefs)
            context.deleteSharedPreferences(legacyPrefsName)
        }
        encryptedPrefs
    }

    private fun createEncryptedPrefs(): SharedPreferences = EncryptedSharedPreferences.create(
        context,
        prefsName,
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    @Suppress("UNCHECKED_CAST")
    private fun SharedPreferences.copyTo(dest: SharedPreferences) = with(dest.edit()) {
        for (entry in all.entries) {
            val value = entry.value ?: continue
            val key = entry.key
            when (value) {
                is String -> putString(key, value)
                is Set<*> -> putStringSet(key, value as Set<String>)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
            }
        }
        apply()
    }

    override fun getAll(): MutableMap<String, *> = prefs.all

    override fun getString(
        key: String?,
        defValue: String?
    ): String? = prefs.getString(key, defValue)

    override fun getStringSet(
        key: String?,
        defValues: MutableSet<String>?
    ): MutableSet<String>? = prefs.getStringSet(key, defValues)

    override fun getInt(
        key: String?,
        defValue: Int
    ): Int = prefs.getInt(key, defValue)

    override fun getLong(
        key: String?,
        defValue: Long
    ): Long = prefs.getLong(key, defValue)

    override fun getFloat(
        key: String?,
        defValue: Float
    ): Float = prefs.getFloat(key, defValue)

    override fun getBoolean(
        key: String?,
        defValue: Boolean
    ): Boolean = prefs.getBoolean(key, defValue)

    override fun contains(key: String?): Boolean = prefs.contains(key)

    override fun edit(): SharedPreferences.Editor = prefs.edit()

    override fun registerOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener?
    ) {
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun unregisterOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener?
    ) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener)
    }
}

fun Context.getEncryptedSharedPreferences(
    prefsName: String,
    legacyPrefsName: String? = null,
): SharedPreferences = EncryptedPrefs(
    context = this,
    prefsName = prefsName,
    legacyPrefsName = legacyPrefsName
)
