package kz.rdd.profile.data.network

import com.google.gson.annotations.SerializedName
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.profile.data.model.FavDto
import kz.rdd.profile.data.model.UserProfileDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileApi {
    @GET("user-profile/")
    suspend fun getUserProfile() : Outcome<UserProfileDto>

    @PATCH("user-profile/")
    suspend fun changeProfile(
        @Body profile: RequestBody
    ) : Outcome<Unit>

    @POST("auth/password-reset-send-code/")
    suspend fun send(
        @Body request: Send,
    ) : Outcome<SendResponseDto>

    @POST("auth/password-varify-code/")
    suspend fun changePass(
        @Body request: ChangePass
    ) : Outcome<Unit>

    @GET("favorites/list/")
    suspend fun getFavList() : Outcome<List<FavDto>>

    @POST("favorites/{id}/option/")
    suspend fun deleteFavProduct(
        @Path("id") id: Int
    ): Outcome<Unit>
}


data class SendResponseDto(
    @SerializedName("token") val token: String,
)

data class SendResponse(
    val token: String,
)

data class Send(
    @SerializedName("email") val email: String,
)

class ChangePass(
    @SerializedName("password") val password: String,
    @SerializedName("code") val code: String,
    @SerializedName("token") val otpToken: String?,
)