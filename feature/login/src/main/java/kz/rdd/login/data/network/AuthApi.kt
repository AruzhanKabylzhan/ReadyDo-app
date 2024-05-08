package kz.rdd.login.data.network

import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.login.data.model.OtpSendResponse
import kz.rdd.login.data.model.RefreshTokenRequest
import kz.rdd.login.data.model.SendCode
import kz.rdd.login.data.model.SetPasswordSuccessResponse
import kz.rdd.login.data.model.TokenDto
import kz.rdd.login.data.model.TokenRequest
import kz.rdd.login.data.model.VerifyResponse
import kz.rdd.login.data.model.VerifySmsRequest
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/token/")
    suspend fun getToken(
        @Body request: TokenRequest
    ): Outcome<TokenDto>

    @POST("auth/password-reset-send-code/")
    suspend fun sendCode(
        @Body data: SendCode
    ): Outcome<OtpSendResponse>

    @POST("auth/password-varify-code/")
    suspend fun verifySms(
        @Body request: VerifySmsRequest
    ): Outcome<SetPasswordSuccessResponse>

    @POST("auth/token/refresh/")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): Outcome<TokenDto>

    @POST("auth/register/")
    suspend fun registerEmployee(
        @Body request: RequestBody,
    ): Outcome<Unit>



}