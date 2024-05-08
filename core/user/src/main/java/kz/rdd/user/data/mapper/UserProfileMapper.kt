package kz.rdd.core.user.data.mapper

import kz.rdd.core.user.data.model.BusinessmanUserProfileDto
import kz.rdd.core.user.data.model.EmployeeUserProfileDto
import kz.rdd.core.user.domain.model.BusinessmanProfile
import kz.rdd.core.user.domain.model.EmployeeUserProfile
import kz.rdd.core.utils.exception.orMappingExceptionOn
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal class UserProfileMapper {
    fun map(dto: EmployeeUserProfileDto): EmployeeUserProfile {
        val (start, end) = dto.todaySchedule?.mapTodaySchedule()
            .orMappingExceptionOn("todaySchedule", dto)
        return EmployeeUserProfile(
            id = dto.id.orMappingExceptionOn("id", dto),
            firstName = dto.firstName,
            lastName = dto.lastName,
            middleName = dto.middleName,
            phoneNumber = dto.phoneNumber,
            email = dto.email,
            avatar = dto.avatar,
            language = dto.language,
            role = EmployeeUserProfile.Role(
                roleId = dto.role?.roleId.orMappingExceptionOn("role_id", dto),
                role = dto.role?.role.orEmpty(),
                title = dto.role?.title.orEmpty(),
                departmentId = dto.role?.departmentId ?: 0,
                zones = dto.role?.zones?.map {
                    EmployeeUserProfile.Zone(
                        latitude = it.latitude.orMappingExceptionOn("latitude", dto),
                        longitude = it.longitude.orMappingExceptionOn("longitude", dto),
                        radius = it.radius ?: 0,
                        address = it.address.orEmpty(),
                    )
                }.orEmpty(),
                departmentName = dto.role?.departmentName.orEmpty(),
            ),
            timesheet = EmployeeUserProfile.Timesheet(
                onTime = dto.timesheet?.onTime ?: 0,
                absent = dto.timesheet?.absent ?: 0,
                lateMinutes = dto.timesheet?.lateMinutes ?: 0.0,
            ),
            selectedCompany = dto.selectedCompany ?: -1,
            todayScheduleStart = start,
            todayScheduleEnd = end,
            score = dto.score ?: 0,
            notifications = dto.notifications ?: 0
        )
    }

    fun map(dto: BusinessmanUserProfileDto): BusinessmanProfile {
        return BusinessmanProfile(
            id = dto.id.orMappingExceptionOn("id", dto),
            firstName = dto.firstName,
            lastName = dto.lastName,
            middleName = dto.middleName,
            phoneNumber = dto.phoneNumber,
            email = dto.email,
            avatar = dto.avatar,
            language = dto.language,
            selectedCompany = BusinessmanProfile.SelectedCompany(
                id = dto.selectedCompany?.id ?: 0,
                name = dto.selectedCompany?.name.orEmpty(),
                inviteCode = dto.selectedCompany?.inviteCode.orEmpty(),
                yearsOfWork = dto.selectedCompany?.yearsOfWork ?: 0,
                isActive = dto.selectedCompany?.isActive.orMappingExceptionOn("isActive", dto),
                maxEmployeeQty = dto.selectedCompany?.maxEmployeeQty ?: 0
            ),
            balance = dto.balance ?: 0,
            pendingUsers = dto.pendingUsers ?: 0,
            inviteLink = dto.inviteLink,
            notifications = dto.notifications ?: 0,
            role = BusinessmanProfile.Role(
                roleName = dto.role?.roleName.orEmpty()
            )
        )
    }

    private fun String.mapTodaySchedule(): Pair<LocalDateTime?, LocalDateTime?> {
        if (this.isEmpty()) return null to null
        val (start, end) = split("-")
            .map {
                it.trim().parseTodayScheduleTime()
            }
        return start to end
    }

    private fun String.parseTodayScheduleTime(): LocalDateTime {
        return LocalDateTime.parse(
            this,
            DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
        )
    }
}
