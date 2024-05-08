package kz.rdd.login.domain.repository

import kz.rdd.core.network.TokenData
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.login.data.model.OtpSendResponse
import kz.rdd.login.domain.model.OtpData
import kz.rdd.login.domain.model.RegisterEmployeeRequest
import kz.rdd.login.domain.model.SendSmsRequestData
import kz.rdd.login.domain.model.VerifySmsData
import kz.rdd.login.domain.model.VerifySmsSuccessData

interface AuthRepository {

    suspend fun sendCode(
        request: SendSmsRequestData,
    ): Outcome<OtpSendResponse>

    suspend fun verifySms(
        password: String,
        code: String,
        token: String?,
    ): Outcome<VerifySmsSuccessData>

    suspend fun getToken(phone: String, password: String): Outcome<TokenData>

    suspend fun refreshToken(refresh: String): Outcome<TokenData>

    suspend fun getRegisterEmployee(request: RegisterEmployeeRequest): Outcome<Unit>

    fun setToken(tokenData: TokenData)
}