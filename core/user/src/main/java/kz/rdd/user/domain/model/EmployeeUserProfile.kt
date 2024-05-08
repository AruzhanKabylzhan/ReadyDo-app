package kz.rdd.core.user.domain.model

import kz.rdd.core.utils.ext.getFullName
import java.time.LocalDateTime

data class EmployeeUserProfile(
    override val id: Int,
    val firstName: String?,
    val lastName: String?,
    val middleName: String?,
    val phoneNumber: String?,
    val email: String?,
    override val avatar: String?,
    val language: String?,
    val role: Role,
    val selectedCompany: Int,
    val todayScheduleStart: LocalDateTime?,
    val todayScheduleEnd: LocalDateTime?,
    override val score: Int,
    val timesheet: Timesheet,
    val notifications: Int,
) : CommonUserDetails {
    val fullName = getFullName(
        firstName = firstName,
        lastName = lastName,
        middleName = middleName
    )
    data class Role(
        val roleId: Int,
        val role: String?,
        val title: String,
        val departmentId: Int?,
        val departmentName: String,
        val zones: List<Zone>,
    )

    data class Zone(
        val latitude: Double,
        val longitude: Double,
        val radius: Int,
        val address: String,
    )

    data class Timesheet(
        val onTime: Int,
        val absent: Int,
        val lateMinutes: Double,
    )

    override val name: String = fullName
    override val roleName: String = role.title
    override val departmentName: String = role.departmentName

    override val onTime: Int = timesheet.onTime
    override val absent: Int = timesheet.absent
    override val lateMinutes: Double = timesheet.lateMinutes
}
