package kz.rdd.home.data

import com.google.gson.annotations.SerializedName

data class ChefDto (
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("grade") val grade: String?,
)