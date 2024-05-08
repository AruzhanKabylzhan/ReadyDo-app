package kz.rdd.store

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime

@Parcelize
data class UserDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("phone_number") val phoneNumber: String?,
    @SerializedName("avatar") val avatar: String?,
) : Parcelable {
    val fullName get() = firstName
        ?.plus(" ")
        .orEmpty()
        .plus(lastName.orEmpty())
        .trim()
}
