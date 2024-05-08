package kz.rdd.core.user.domain

import kz.rdd.core.user.domain.repository.UsersRepository

class GetCompanyUsersUseCase(
    private val usersRepository: UsersRepository,
) {

    suspend fun getUsers(company: Int) = usersRepository.getUsers(company)
}
