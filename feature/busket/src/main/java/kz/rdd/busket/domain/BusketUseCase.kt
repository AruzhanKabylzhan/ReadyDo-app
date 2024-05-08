package kz.rdd.busket.domain

import kz.rdd.busket.domain.repo.BusketRepo

class BusketUseCase(
    private val repo: BusketRepo
) {
    suspend fun getBusketList(status: Int) = repo.getBusketList(status)
    suspend fun getBusketProducts(id: Int) = repo.getBusketProducts(id)
    suspend fun removeFood(id: Int) = repo.removeFood(id)
    suspend fun changeStatus(id: Int, status: Int) = repo.changeStatus(id, status)

    suspend fun foodAdd(id: Int, quantity: Int) = repo.foodAdd(id, quantity)
    suspend fun getProfile() = repo.getProfile()

}