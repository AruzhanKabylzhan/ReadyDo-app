package kz.rdd.core.user.domain.repository

import kz.rdd.core.user.domain.model.BusinessmanProfile
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.core.user.domain.model.EmployeeUserProfile

interface UserProfileRepository {
    suspend fun getEmployeeUserProfile(): Outcome<EmployeeUserProfile>
    suspend fun getBusinessmanUserProfile(): Outcome<BusinessmanProfile>
}