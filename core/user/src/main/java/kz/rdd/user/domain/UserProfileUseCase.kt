package kz.rdd.core.user.domain

import kz.rdd.core.user.domain.repository.UserProfileRepository

class UserProfileUseCase(
    private val repository: UserProfileRepository,
) {
    suspend fun getEmployeeUserProfile() = repository.getEmployeeUserProfile()

    suspend fun getBusinessmanProfile() = repository.getBusinessmanUserProfile()
}