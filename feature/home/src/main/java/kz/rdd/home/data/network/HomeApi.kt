package kz.rdd.home.data.network

import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.home.data.ChefDto
import retrofit2.http.GET

interface HomeApi {
    @GET("chef/")
    suspend fun getChefs() : Outcome<List<ChefDto>>
}