package kz.rdd.core.user.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.rdd.core.user.data.mapper.PersonMapper
import kz.rdd.core.user.data.network.UserApi
import kz.rdd.core.user.domain.model.Person
import kz.rdd.core.user.domain.repository.UsersRepository
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.core.utils.outcome.map

internal class UsersRepositoryImpl(
    private val userApi: UserApi,
    private val personMapper: PersonMapper,
) : UsersRepository {
    override suspend fun getUsers(company: Int): Outcome<List<Person>> {
        return withContext(Dispatchers.IO) {
            userApi.getCompanyUsers(company.toString()).map {
                it.map(personMapper::map)
            }
        }
    }
}
