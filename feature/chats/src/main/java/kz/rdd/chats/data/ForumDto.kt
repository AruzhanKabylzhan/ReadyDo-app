package kz.rdd.chats.data

import com.google.gson.annotations.SerializedName
import java.time.OffsetDateTime

data class ForumDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("photo") val photo: String?,
    @SerializedName("description") val description: String,
    @SerializedName("username") val username: String,
    @SerializedName("grade") val grade: String,
)

data class ForumMessageDto(
    @SerializedName("id") val id: Int,
    @SerializedName("created_at") val createdAt: OffsetDateTime,
    @SerializedName("message") val message: String?,
    @SerializedName("forum") val forum: Int,
    @SerializedName("username") val username: String?,
)