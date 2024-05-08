package kz.rdd.core.user.domain.model

object UserRoles {
    const val OWNER = "owner"
    const val SUPERUSER = "superuser"
    const val HR = "hr"
    const val OBSERVER = "observer"
    const val HEAD_OF_HR_DEPARTMENT = "head_of_hr_department"
    const val CO_OWNER = "co_owner"

    val STATISTICS_VIEWERS = setOf(
        OWNER, SUPERUSER, HR, OBSERVER, HEAD_OF_HR_DEPARTMENT
    )
}