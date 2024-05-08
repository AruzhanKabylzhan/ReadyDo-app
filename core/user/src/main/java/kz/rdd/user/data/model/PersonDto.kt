package kz.rdd.core.user.data.model

import com.google.gson.annotations.SerializedName

class PersonDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("position") val position: String?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("middle_name") val middleName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("avatar") val avatar: String?,
)
