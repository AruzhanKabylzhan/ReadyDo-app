package kz.rdd.core.user.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kz.rdd.core.user.domain.model.EmployeeUserProfile
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.core.utils.outcome.asSuccess
import kz.rdd.core.utils.outcome.map
import kz.rdd.core.utils.outcome.successOrNull
import kz.rdd.store.UserSessionCleaner

data class EmployeeUserProfileState(
    val userProfileOutcome: Outcome<EmployeeUserProfile>? = null,
    val loading: Boolean = false,
)

class EmployeeUserProfileHolder(
    private val userDetailsUseCase: UserProfileUseCase,
) : UserSessionCleaner {
    val userProfile = MutableStateFlow(EmployeeUserProfileState())

    suspend fun load(cacheable: Boolean = false): Outcome<EmployeeUserProfile> {
        val successValue = userProfile.value.userProfileOutcome?.successOrNull
        if (cacheable &&  successValue != null) {
            return successValue.asSuccess
        }
        userProfile.update {
            it.copy(
                loading = true,
            )
        }
        val userProfileOutcome = userDetailsUseCase.getEmployeeUserProfile()
        userProfile.update {
            it.copy(
                userProfileOutcome = userProfileOutcome,
                loading = false,
            )
        }
        return userProfileOutcome
    }

    fun updateUserProfile(onUpdateProfile: EmployeeUserProfile.() -> EmployeeUserProfile) {
        userProfile.update {
            it.copy(
                userProfileOutcome = it.userProfileOutcome?.map { profile ->
                    profile.onUpdateProfile()
                }
            )
        }
    }

    override suspend fun clean(authToken: String) {
        userProfile.value = EmployeeUserProfileState()
    }
}