package kz.rdd.store

import kotlinx.coroutines.supervisorScope

class UserLogoutCleanUseCase(
    private val userStore: UserStore,
    private val getCleaners: () -> List<UserSessionCleaner>,
) {
    suspend fun cleanAll() = supervisorScope {
        val authToken = userStore.accessToken
        getCleaners().forEach {
            it.clean(authToken.orEmpty())
        }
        userStore.clearUserSession()
    }
}
