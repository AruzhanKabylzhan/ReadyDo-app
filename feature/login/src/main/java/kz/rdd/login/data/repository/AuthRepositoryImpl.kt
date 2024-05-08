package kz.rdd.login.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.rdd.core.network.TokenData
import kz.rdd.core.utils.ext.addFormDataPartWithSkippingNull
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.core.utils.outcome.map
import kz.rdd.login.data.model.OtpSendResponse
import kz.rdd.login.data.model.RefreshTokenRequest
import kz.rdd.login.data.model.SendCode
import kz.rdd.login.data.model.TokenDto
import kz.rdd.login.data.model.TokenRequest
import kz.rdd.login.data.model.VerifySmsRequest
import kz.rdd.login.data.network.AuthApi
import kz.rdd.login.domain.model.OtpData
import kz.rdd.login.domain.model.RegisterEmployeeRequest
import kz.rdd.login.domain.model.SendSmsRequestData
import kz.rdd.login.domain.model.VerifySmsData
import kz.rdd.login.domain.model.VerifySmsSuccessData
import kz.rdd.login.domain.repository.AuthRepository
import kz.rdd.store.UserStore
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

internal class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val userStore: UserStore,
) : AuthRepository {

    override suspend fun sendCode(
        request: SendSmsRequestData,
    ) = withContext(Dispatchers.IO) {
        authApi.sendCode(
            SendCode(
                request.email
            )
        ).map {
            OtpSendResponse(
                token = it.token
            )
        }
    }

    override suspend fun verifySms(
        password: String,
        code: String,
        token: String?
    ): Outcome<VerifySmsSuccessData> = withContext(Dispatchers.IO) {
        authApi.verifySms(VerifySmsRequest(password = password, code = code, otpToken = token))
            .map {
                VerifySmsSuccessData(it.message.orEmpty())
            }
    }

    override suspend fun getToken(phone: String, password: String) = withContext(Dispatchers.IO) {
        authApi.getToken(TokenRequest(phone, password))
            .map {
                val token = mapToken(it)
                setToken(token)
                userStore.userDetails = it.userDetail
                token
            }
    }

    override suspend fun refreshToken(refresh: String) = withContext(Dispatchers.IO) {
        authApi.refreshToken(RefreshTokenRequest(refresh))
            .map {
                val token = mapToken(it)
                setToken(token)
                token
            }
    }

    override fun setToken(tokenData: TokenData) {
        userStore.setTokens(
            access = tokenData.access,
            refresh = tokenData.refresh
        )
    }

    private fun mapToken(tokenDto: TokenDto) = TokenData(
        access = tokenDto.accessToken.orEmpty(),
        refresh = tokenDto.refreshToken.orEmpty()
    )

    override suspend fun getRegisterEmployee(request: RegisterEmployeeRequest) =
        withContext(Dispatchers.IO) {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPartWithSkippingNull("avatar", request.avatar?.let { File(it) })
                .addFormDataPartWithSkippingNull("password", request.password)
                .addFormDataPartWithSkippingNull("first_name", request.firstName)
                .addFormDataPartWithSkippingNull("middle_name", request.middleName)
                .addFormDataPartWithSkippingNull("last_name", request.lastName)
                .addFormDataPartWithSkippingNull("email", request.email)
                .addFormDataPartWithSkippingNull("phone_number", request.phoneNumber)
                .addFormDataPartWithSkippingNull("address", request.address)
                .addFormDataPartWithSkippingNull("about_yourself", request.yourself)
                .addFormDataPartWithSkippingNull("username", request.username)
                .build()

            authApi.registerEmployee(requestBody)
        }
}
