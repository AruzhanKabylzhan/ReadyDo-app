package kz.rdd.core.user.data.model

import com.google.gson.annotations.SerializedName

class EmployeesListDto(
    @SerializedName("results") val results: List<Result>?,
) {
    data class Result(
        @SerializedName("department") val department: Department?,
        @SerializedName("grade") val grade: Int?,
        @SerializedName("id") val id: Int?,
        @SerializedName("role") val role: Int?,
        @SerializedName("score") val score: Int?,
        @SerializedName("score_previous") val scorePrevious: Int?,
        @SerializedName("title") val title: String?,
        @SerializedName("user") val user: User?
    ) {
        data class User(
            @SerializedName("avatar") val avatar: String?,
            @SerializedName("first_name") val firstName: String?,
            @SerializedName("id") val id: Int?,
            @SerializedName("last_name") val lastName: String?,
        )

        data class Department(
            @SerializedName("id") val id: Int?,
            @SerializedName("name") val name: String?
        )
    }
}