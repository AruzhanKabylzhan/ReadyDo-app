package kz.rdd.login.domain

import kz.rdd.core.network.TokenData
import kz.rdd.core.network.TokenUseCase
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.login.data.model.OtpSendResponse
import kz.rdd.login.domain.model.OtpData
import kz.rdd.login.domain.model.RegisterEmployeeRequest
import kz.rdd.login.domain.model.SendSmsRequestData
import kz.rdd.login.domain.model.VerifySmsData
import kz.rdd.login.domain.model.VerifySmsSuccessData
import kz.rdd.login.domain.repository.AuthRepository

interface AuthUseCase : TokenUseCase {

    suspend fun sendCode(
        request: SendSmsRequestData
    ): Outcome<OtpSendResponse>

    suspend fun verifySms(
        password: String,
        code: String,
        token: String?,
    ): Outcome<VerifySmsSuccessData>

    suspend fun getRegisterEmployee(request: RegisterEmployeeRequest): Outcome<Unit>

}

internal class AuthUseCaseImpl(
    private val authRepository: AuthRepository,
) : AuthUseCase {

    override suspend fun sendCode(
        request: SendSmsRequestData
    ) = authRepository.sendCode(request)

    override suspend fun verifySms(
        password: String,
        code: String,
        token: String?
    ): Outcome<VerifySmsSuccessData> = authRepository.verifySms(password, code, token)

    override suspend fun getRegisterEmployee(request: RegisterEmployeeRequest) = authRepository.getRegisterEmployee(request)

    override suspend fun getToken(
        email: String,
        password: String
    ) = authRepository.getToken(email, password)

    override suspend fun refreshToken(refresh: String): Outcome<TokenData> = authRepository.refreshToken(refresh)

    override fun setToken(tokenData: TokenData) = authRepository.setToken(tokenData)
}
