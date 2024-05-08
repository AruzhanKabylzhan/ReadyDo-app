package kz.rdd.home.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.core.utils.outcome.map
import kz.rdd.home.data.network.HomeApi
import kz.rdd.home.domain.Chef
import kz.rdd.home.domain.repo.HomeRepo

class HomeRepoImpl(
    private val homeApi: HomeApi
) : HomeRepo {
    override suspend fun getChefs(): Outcome<List<Chef>> = withContext(Dispatchers.IO) {
        homeApi.getChefs().map {
            it.map {
                Chef(
                    id = it.id,
                    username = it.username,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    avatar = it.avatar,
                    grade = it.grade.orEmpty(),
                )
            }
        }
    }
}