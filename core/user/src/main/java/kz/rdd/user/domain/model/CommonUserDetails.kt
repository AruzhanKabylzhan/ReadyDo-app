package kz.rdd.core.user.domain.model

interface CommonUserDetails {
    val id: Int
    val avatar: String?
    val name: String
    val roleName: String
    val departmentName: String
    val score: Int
    val onTime: Int
    val absent: Int
    val lateMinutes: Double
}
