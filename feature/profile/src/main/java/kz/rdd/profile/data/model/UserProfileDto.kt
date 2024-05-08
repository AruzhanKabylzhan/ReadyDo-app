package kz.rdd.profile.data.model

import com.google.gson.annotations.SerializedName

data class UserProfileDto (
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("grade") val grade: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("about_yourself") val aboutYourself: String?,
    @SerializedName("phone_number") val phoneNumber: String?,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("middle_name") val middleName: String?,
)

data class RequestProfile(
    val firstName: String,
    val lastName: String,
    val avatar: String?,
    val email: String,
    val aboutMySelf: String,
    val address: String,
    val phoneNumber: String,
)