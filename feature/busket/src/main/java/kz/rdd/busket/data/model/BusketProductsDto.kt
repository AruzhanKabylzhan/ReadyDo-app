package kz.rdd.busket.data.model

import com.google.gson.annotations.SerializedName

data class BusketProductsDto (
    @SerializedName("id") val id: Int,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("order") val order: Int,
    @SerializedName("food") val food: Int,
    @SerializedName("product_info") val productInfo: ProductInfo,
) {
    data class ProductInfo(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("price") val price: Int,
        @SerializedName("ingredients") val ingredients: String,
        @SerializedName("cuisine") val cuisine: Int,
        @SerializedName("taste") val taste: Int,
        @SerializedName("grade") val grade: String,
        @SerializedName("photo") val photo: String,
        @SerializedName("user") val user: Int,
        @SerializedName("username") val username: String,
    )
}