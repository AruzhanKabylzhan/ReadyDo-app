package kz.rdd.busket.domain.model

import com.google.gson.annotations.SerializedName
import java.time.OffsetDateTime

data class BusketList(
    val id: Int,
    val status: Int,
    val dateOrdered: OffsetDateTime,
    val totalPrice: Int,
    val customer: Customer,
) {
    data class Customer(
        val id: Int,
        val email: String,
        val firstName: String,
        val lastName: String,
        val address: String,
    )
}