package kz.rdd.catalog.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.rdd.catalog.data.network.CatalogApi
import kz.rdd.catalog.domain.Food
import kz.rdd.catalog.domain.repo.CatalogRepo
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.core.utils.outcome.map

class CatalogRepoImpl(
    private val api: CatalogApi
) : CatalogRepo {
    override suspend fun getFoods(
        cuisineIds: List<Int>?,
        tasteIds: List<Int>?,
        startPrice: Int?,
        endPrice: Int?,
        userId: Int?
    ): Outcome<List<Food>> = withContext(Dispatchers.IO) {
        api.getFoods(cuisineIds, tasteIds, startPrice, endPrice, userId).map {
            it.map {
                Food(
                    id = it.id,
                    name = it.name.orEmpty(),
                    price = it.price,
                    ingredients = it.ingredients.orEmpty(),
                    cuisine = it.cuisine,
                    taste = it.taste,
                    grade = it.grade.orEmpty(),
                    photo = it.photo.orEmpty(),
                    user = it.user,
                    username = it.username.orEmpty(),
                )
            }
        }
    }

    override suspend fun addToFav(id: Int): Outcome<Unit> = withContext(Dispatchers.IO) {
        api.addToFav(id)
    }
}