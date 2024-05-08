package kz.rdd.catalog.data.network

import kz.rdd.catalog.data.FoodDto
import kz.rdd.core.utils.outcome.Outcome
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CatalogApi {
    @GET("foods/")
    suspend fun getFoods(
        @Query("cuisine_ids") cuisineIds: List<Int>?,
        @Query("taste_ids") tasteId: List<Int>?,
        @Query("start_price") startPrice: Int?,
        @Query("end_price") endPrice: Int?,
        @Query("user_id") userId: Int?,
    ) : Outcome<List<FoodDto>>

    @POST("favorites/{id}/option/")
    suspend fun addToFav(
        @Path("id") id: Int,
    ) : Outcome<Unit>
}