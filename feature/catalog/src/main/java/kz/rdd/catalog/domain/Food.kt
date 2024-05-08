package kz.rdd.catalog.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val id: Int,
    val name: String,
    val price: Int,
    val ingredients: String,
    val cuisine: Int,
    val taste: Int,
    val grade: String,
    val photo: String,
    val user: Int,
    val username: String,
) : Parcelable