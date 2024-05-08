package kz.rdd.busket.domain.model

import com.google.gson.annotations.SerializedName

data class BusketProducts(
    val id: Int,
    val quantity: Int,
    val order: Int,
    val food: Int,
    val productInfo: ProductInfo,
) {
    data class ProductInfo(
        val id: Int,
        val name: String,
        val price: Int,
        val ingredients: String,
        val cuisine: Int,
        val taste: Int,
        val grade: String,
        val photo: String?,
        val user: Int,
        val username: String,
    )
}