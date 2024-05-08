package kz.rdd.core.network

import kz.rdd.core.utils.outcome.Outcome

data class TokenData(
    val access: String,
    val refresh: String,
)

interface TokenUseCase {
    suspend fun getToken(email: String, password: String): Outcome<TokenData>
    suspend fun refreshToken(refresh: String): Outcome<TokenData>
    fun setToken(tokenData: TokenData)
}
