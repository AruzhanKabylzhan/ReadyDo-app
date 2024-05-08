package kz.rdd.catalog.data

import com.google.gson.annotations.SerializedName

data class FoodDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("price") val price: Int,
    @SerializedName("ingredients") val ingredients: String?,
    @SerializedName("cuisine") val cuisine: Int,
    @SerializedName("taste") val taste: Int,
    @SerializedName("grade") val grade: String?,
    @SerializedName("photo") val photo: String?,
    @SerializedName("user") val user: Int,
    @SerializedName("username") val username: String?,
)