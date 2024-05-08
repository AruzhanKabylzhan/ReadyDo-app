package kz.rdd.core.user.data.model

import com.google.gson.annotations.SerializedName

internal class BusinessmanUserProfileDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("middle_name") val middleName: String?,
    @SerializedName("phone_number") val phoneNumber: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("selected_company") val selectedCompany: SelectedCompany?,
    @SerializedName("balance") val balance: Int?,
    @SerializedName("pending_users") val pendingUsers: Int?,
    @SerializedName("invite_link") val inviteLink: String?,
    @SerializedName("notifications") val notifications: Int?,
    @SerializedName("role") val role: Role?,
) {
    data class Role(
        @SerializedName("role") val roleName: String?
    )

    data class SelectedCompany(
        @SerializedName("id") val id: Int?,
        @SerializedName("name") val name: String?,
        @SerializedName("invite_code") val inviteCode: String?,
        @SerializedName("years_of_work") val yearsOfWork: Int?,
        @SerializedName("is_active") val isActive: Boolean?,
        @SerializedName("max_employees_qty") val maxEmployeeQty: Int?,
    )
}
