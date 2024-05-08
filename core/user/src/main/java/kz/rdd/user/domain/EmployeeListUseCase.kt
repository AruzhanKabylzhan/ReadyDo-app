package kz.rdd.core.user.domain

import kz.rdd.core.user.domain.repository.EmployeesListRepository

class EmployeesListUseCase(
    private val repository: EmployeesListRepository
) {

    suspend fun getEmployeesList(
        company: String,
        departments: List<Int> = emptyList()
    ) = repository.getEmployeesList(
        company = company,
        departments = departments
    )
}