package kz.rdd.catalog.domain.repo

import kz.rdd.catalog.domain.Food
import kz.rdd.core.utils.outcome.Outcome

interface CatalogRepo {
    suspend fun getFoods(
        cuisineIds: List<Int>?,
        tasteIds: List<Int>?,
        startPrice: Int?,
        endPrice: Int?,
        userId: Int?,
    ) : Outcome<List<Food>>

    suspend fun addToFav(id: Int) : Outcome<Unit>
}