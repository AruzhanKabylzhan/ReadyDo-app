package kz.rdd.profile.data.model

import com.google.gson.annotations.SerializedName

data class FavDto(
    @SerializedName("id") val id: Int,
    @SerializedName("food") val food: Food,
) {
    data class Food(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String?,
        @SerializedName("price") val price: Int,
        @SerializedName("ingredients") val ingredients: String?,
        @SerializedName("cuisine") val cuisine: Int,
        @SerializedName("taste") val taste: Int,
        @SerializedName("grade") val grade: String?,
        @SerializedName("photo") val photo: String?,
        @SerializedName("username") val username: String,
    )
}