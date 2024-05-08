package kz.rdd.core.user.data.model

import com.google.gson.annotations.SerializedName

internal class EmployeeUserProfileDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("middle_name") val middleName: String?,
    @SerializedName("phone_number") val phoneNumber: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("role") val role: Role?,
    @SerializedName("selected_company") val selectedCompany: Int?,
    @SerializedName("today_schedule") val todaySchedule: String?,
    @SerializedName("score") val score: Int?,
    @SerializedName("timesheet") val timesheet: Timesheet?,
    @SerializedName("notifications") val notifications: Int?,
) {
    class Role(
        @SerializedName("role_id") val roleId: Int?,
        @SerializedName("role") val role: String?,
        @SerializedName("title") val title: String?,
        @SerializedName("zones") val zones: List<Zone>?,
        @SerializedName("department_id") val departmentId: Int?,
        @SerializedName("department_name") val departmentName: String?,
    )

    class Zone(
        @SerializedName("latitude") val latitude: Double?,
        @SerializedName("longitude") val longitude: Double?,
        @SerializedName("radius") val radius: Int?,
        @SerializedName("address") val address: String?,
    )

    class Timesheet(
        @SerializedName("on_time") val onTime: Int?,
        @SerializedName("absent") val absent: Int?,
        @SerializedName("late_minutes") val lateMinutes: Double?,
    )
}
