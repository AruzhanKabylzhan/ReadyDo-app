package kz.rdd.login.data.model

import com.google.gson.annotations.SerializedName
import kz.rdd.store.UserDetail

class TokenDto(
    @SerializedName("access") val accessToken: String?,
    @SerializedName("refresh") val refreshToken: String?,
    @SerializedName("user") val userDetail: UserDetail,
)

class OtpSendResponse(
    @SerializedName("token") val token: String?,
)

internal class VerifyResponse(
    @SerializedName("reset_token", alternate = ["otp_token"]) val token: String?,
)

class SetPasswordSuccessResponse(
    @SerializedName("message") val message: String?,
)

class TokenRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("phone_number") val phoneNumber: String = "0",
)

class RefreshTokenRequest(
    @SerializedName("refresh") val refreshToken: String,
)

class SendCode(
    @SerializedName("email") val email: String,
)

class VerifySmsRequest(
    @SerializedName("password") val password: String,
    @SerializedName("code") val code: String,
    @SerializedName("token") val otpToken: String?,
)

internal class ResetPasswordRequest(
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("reset_token") val resetToken: String,
    @SerializedName("new_password") val newPassword: String,
)
