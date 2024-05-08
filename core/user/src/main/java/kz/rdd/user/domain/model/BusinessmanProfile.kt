package kz.rdd.core.user.domain.model

import kz.rdd.core.utils.ext.getFullName

data class BusinessmanProfile (
    val id: Int,
    val firstName: String?,
    val lastName: String?,
    val middleName: String?,
    val phoneNumber: String?,
    val email: String?,
    val avatar: String?,
    val language: String?,
    val selectedCompany: SelectedCompany,
    val pendingUsers: Int,
    val balance: Int,
    val inviteLink: String?,
    val notifications: Int,
    val role: Role,
) {
    val fullName = getFullName(
        firstName = firstName,
        lastName = lastName,
        middleName = middleName
    )

    data class Role(
        val roleName: String,
    )

    data class SelectedCompany(
        val id: Int,
        val name: String,
        val inviteCode: String,
        val yearsOfWork: Int,
        val isActive: Boolean,
        val maxEmployeeQty: Int,
    )
}
