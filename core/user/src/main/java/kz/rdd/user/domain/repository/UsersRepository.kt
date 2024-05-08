package kz.rdd.core.user.domain.repository

import kz.rdd.core.user.domain.model.Person
import kz.rdd.core.utils.outcome.Outcome

interface UsersRepository {
    suspend fun getUsers(company: Int): Outcome<List<Person>>
}
