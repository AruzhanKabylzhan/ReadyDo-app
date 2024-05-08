package kz.rdd.catalog.domain

import kz.rdd.catalog.domain.repo.CatalogRepo

class CatalogUseCase(
    private val repo: CatalogRepo
) {
    suspend fun getFoods(
        cuisineIds: List<Int>?,
        tasteIds: List<Int>?,
        startPrice: Int?,
        endPrice: Int?,
        userId: Int?,
    ) = repo.getFoods(cuisineIds, tasteIds, startPrice, endPrice, userId)

    suspend fun addToFav(id: Int) = repo.addToFav(id)
}