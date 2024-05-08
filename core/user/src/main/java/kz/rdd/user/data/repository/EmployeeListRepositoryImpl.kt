package kz.rdd.core.user.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.rdd.core.user.data.mapper.EmployeesListMapper
import kz.rdd.core.user.data.network.UserApi
import kz.rdd.core.user.domain.model.EmployeesProfile
import kz.rdd.core.user.domain.repository.EmployeesListRepository
import kz.rdd.core.utils.CacheHolder
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.core.utils.outcome.map

internal class EmployeesListRepositoryImpl(
    private val api: UserApi,
    private val employeesListMapper: EmployeesListMapper,
) : EmployeesListRepository {
    private var cacheHolder = CacheHolder<EmployeesProfile>()

    override suspend fun getEmployeesList(
        company: String,
        departments: List<Int>
    ): Outcome<EmployeesProfile> = withContext(Dispatchers.IO) {
        cacheHolder.withCached(
            key = company + departments.sorted().joinToString(),
        ) {
            api.getEmployeesList(
                company = company,
                departments = departments
            ).map(employeesListMapper::map)
        }
    }

    override suspend fun clean(authToken: String) {
        cacheHolder.clean()
    }
}
