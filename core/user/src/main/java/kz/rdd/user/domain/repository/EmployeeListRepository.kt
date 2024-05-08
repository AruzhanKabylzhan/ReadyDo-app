package kz.rdd.core.user.domain.repository

import kz.rdd.core.user.domain.model.EmployeesProfile
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.store.UserSessionCleaner

interface EmployeesListRepository : UserSessionCleaner {

    suspend fun getEmployeesList(
        company: String,
        departments: List<Int> = emptyList()
    ): Outcome<EmployeesProfile>
}