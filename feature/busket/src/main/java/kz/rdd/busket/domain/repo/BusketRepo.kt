package kz.rdd.busket.domain.repo

import kz.rdd.busket.domain.model.BusketList
import kz.rdd.busket.domain.model.BusketProducts
import kz.rdd.core.utils.outcome.Outcome

interface BusketRepo {
    suspend fun getBusketList(status: Int) : Outcome<List<BusketList>>
    suspend fun getBusketProducts(id: Int) : Outcome<List<BusketProducts>>
    suspend fun removeFood(id: Int) : Outcome<Unit>
    suspend fun changeStatus(id: Int, status: Int) : Outcome<Unit>
    suspend fun foodAdd(id: Int, quantity: Int) : Outcome<Unit>
    suspend fun getProfile() : Outcome<UserProfile>
}

data class UserProfile (
    val id: Int,
    val username: String,
    val email: String,
    val grade: String,
    val address: String,
    val aboutYourself: String,
    val phoneNumber: String,
    val avatar: String,
    val firstName: String,
    val lastName: String,
    val middleName: String,
)