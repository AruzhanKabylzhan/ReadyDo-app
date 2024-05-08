package kz.rdd.busket.data.model

import com.google.gson.annotations.SerializedName
import java.time.OffsetDateTime

data class BusketListDto(
    @SerializedName("id") val id: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("date_ordered") val dateOrdered: OffsetDateTime,
    @SerializedName("total_price") val totalPrice: Int,
    @SerializedName("customer") val customer: Customer,
) {
    data class Customer(
        @SerializedName("id") val id: Int,
        @SerializedName("email") val email: String,
        @SerializedName("first_name") val firstName: String,
        @SerializedName("last_name") val lastName: String,
        @SerializedName("address") val address: String,
    )
}