package kz.rdd.store

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kz.rdd.core.utils.BooleanPreference
import kz.rdd.core.utils.GsonPreference
import kz.rdd.core.utils.IntPreference
import kz.rdd.core.utils.Language
import kz.rdd.core.utils.StringPreference

class UserStore(
    sharedPrefs: SharedPreferences,
    gson: Gson,
) : UserSessionCleaner {
    var accessToken by StringPreference(
        sharedPreferences = sharedPrefs,
        key = "user_access_token",
    )

    var refreshToken by StringPreference(
        sharedPreferences = sharedPrefs,
        key = "user_refresh_token",
    )

    var isGotAcquainted by BooleanPreference(
        sharedPreferences = sharedPrefs,
        key = "introduction",
    )

    var userDetails: UserDetail? by GsonPreference(
        sharedPreferences = sharedPrefs,
        gson = gson,
        key = "user_details",
        type = object : TypeToken<UserDetail>() {}.type
    )

    var userCards: List<UserCard>? by GsonPreference(
        sharedPreferences = sharedPrefs,
        gson = gson,
        key = "user_card",
        type = object : TypeToken<List<UserCard>>() {}.type
    )

    var cuisineIds: List<Int>? by GsonPreference(
        sharedPreferences = sharedPrefs,
        gson = gson,
        key = "user_filter_cuisine_ids",
        type = object : TypeToken<List<Int>>() {}.type
    )

    var tasteIds: List<Int>? by GsonPreference(
        sharedPreferences = sharedPrefs,
        gson = gson,
        key = "user_filter_taste_ids",
        type = object : TypeToken<List<Int>>() {}.type
    )

    var startPrice by IntPreference(
        sharedPreferences = sharedPrefs,
        key = "start_price",
        defaultValue = DEFAULT_INT_VALUE,
    )

    var endPrice by IntPreference(
        sharedPreferences = sharedPrefs,
        key = "end_price",
        defaultValue = DEFAULT_INT_VALUE,
    )

    private var _selectedCompanyId by IntPreference(
        sharedPreferences = sharedPrefs,
        key = "user_selected_company_id",
        defaultValue = DEFAULT_INT_VALUE
    )

    val selectedCompanyIdFlow = MutableStateFlow(selectedCompanyId)

    var selectedCompanyId: Int?
        get() = _selectedCompanyId.takeIf { it != DEFAULT_INT_VALUE }
        set(value) {
            val id = value ?: DEFAULT_INT_VALUE
            _selectedCompanyId = id
            selectedCompanyIdFlow.value = id
        }

    private var _language: Language? by GsonPreference(
        sharedPrefs,
        gson,
        Language::class.java,
        defaultValue = Language.English
    )

    val languageFlow = MutableStateFlow(_language ?: Language.English)

    var language
        get() = languageFlow.value
        set(value) {
            languageFlow.value = value
            _language = value
        }

    fun setTokens(access: String, refresh: String) {
        accessToken = access
        refreshToken = refresh
        isLoggedFlow.value = true
    }

    fun clearUserSession() {
        accessToken = null
        refreshToken = null
        userDetails = null
        selectedCompanyId = null
        isLoggedFlow.value = false
    }

    val userType: UserType?
        get() {
            val userDetails = userDetails
            return when {
                userDetails == null -> null
                else -> UserType.EMPLOYEE
            }
        }

    val isLogged get() = !accessToken.isNullOrEmpty()

    val isLoggedFlow = MutableStateFlow(isLogged)
    override suspend fun clean(authToken: String) {
        clearUserSession()
    }

    private companion object {
        const val DEFAULT_INT_VALUE = -1
    }
}
