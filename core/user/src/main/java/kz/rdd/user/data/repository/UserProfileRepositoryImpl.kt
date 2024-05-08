package kz.rdd.core.user.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.rdd.core.user.data.network.UserApi
import kz.rdd.core.user.data.mapper.UserProfileMapper
import kz.rdd.core.user.domain.model.BusinessmanProfile
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.core.utils.outcome.map
import kz.rdd.core.user.domain.model.EmployeeUserProfile
import kz.rdd.core.user.domain.repository.UserProfileRepository

internal class UserProfileRepositoryImpl(
    private val api: UserApi,
    private val mapper: UserProfileMapper,
): UserProfileRepository {

    override suspend fun getEmployeeUserProfile(): Outcome<EmployeeUserProfile> = withContext(Dispatchers.IO) {
        api.getEmployeeUserProfile().map(mapper::map)
    }

    override suspend fun getBusinessmanUserProfile(): Outcome<BusinessmanProfile> = withContext(Dispatchers.IO) {
        api.getBusinessmanUserProfile().map(mapper::map)
    }
}
