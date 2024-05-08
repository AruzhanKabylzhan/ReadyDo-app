package kz.rdd.profile.domain.model

data class FavList(
    val id: Int,
    val food: Food,
) {
    data class Food(
        val id: Int,
        val name: String,
        val price: Int,
        val ingredients: String,
        val cuisine: Int,
        val taste: Int,
        val grade: String,
        val photo: String,
        val username: String,
    )
}