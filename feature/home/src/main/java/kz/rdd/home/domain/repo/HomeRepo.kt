package kz.rdd.home.domain.repo

import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.home.domain.Chef
import retrofit2.http.GET

interface HomeRepo {

    suspend fun getChefs() : Outcome<List<Chef>>
}