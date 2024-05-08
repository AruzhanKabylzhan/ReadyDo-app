package kz.rdd.core.user.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kz.rdd.core.user.domain.model.BusinessmanProfile
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.core.utils.outcome.asSuccess
import kz.rdd.core.utils.outcome.map
import kz.rdd.core.utils.outcome.successOrNull
import kz.rdd.store.UserSessionCleaner

data class BusinessmanUserProfileState(
    val userProfileOutcome: Outcome<BusinessmanProfile>? = null,
    val loading: Boolean = false,
)

class BusinessmanUserProfileHolder(
    private val userDetailsUseCase: UserProfileUseCase,
) : UserSessionCleaner {
    val userProfile = MutableStateFlow(BusinessmanUserProfileState())

    suspend fun load(cacheable: Boolean = false): Outcome<BusinessmanProfile> {
        val successValue = userProfile.value.userProfileOutcome?.successOrNull
        if (cacheable &&  successValue != null) {
            return successValue.asSuccess
        }
        userProfile.update {
            it.copy(
                loading = true,
            )
        }
        val userProfileOutcome = userDetailsUseCase.getBusinessmanProfile()
        userProfile.update {
            it.copy(
                userProfileOutcome = userProfileOutcome,
                loading = false,
            )
        }
        return userProfileOutcome
    }

    fun updateUserProfile(onUpdateProfile: BusinessmanProfile.() -> BusinessmanProfile) {
        userProfile.update {
            it.copy(
                userProfileOutcome = it.userProfileOutcome?.map { profile ->
                    profile.onUpdateProfile()
                }
            )
        }
    }

    override suspend fun clean(authToken: String) {
        userProfile.value = BusinessmanUserProfileState()
    }
}