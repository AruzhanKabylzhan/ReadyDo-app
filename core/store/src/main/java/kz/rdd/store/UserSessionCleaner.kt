package kz.rdd.store

interface UserSessionCleaner {
    suspend fun clean(authToken: String)

}
