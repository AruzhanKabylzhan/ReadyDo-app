package kz.rdd.busket.data.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.rdd.busket.data.mapper.BusketListMapper
import kz.rdd.busket.data.mapper.BusketProductsMapper
import kz.rdd.busket.data.network.BusketApi
import kz.rdd.busket.domain.model.BusketList
import kz.rdd.busket.domain.model.BusketProducts
import kz.rdd.busket.domain.repo.BusketRepo
import kz.rdd.busket.domain.repo.UserProfile
import kz.rdd.core.utils.ext.addFormDataPartWithSkippingNull
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.core.utils.outcome.map
import okhttp3.MultipartBody
import okhttp3.RequestBody

class BusketRepoImpl(
    private val api: BusketApi,
    private val busketListMapper: BusketListMapper,
    private val busketProductsMapper: BusketProductsMapper,
) : BusketRepo {
    override suspend fun getBusketList(status: Int): Outcome<List<BusketList>> =
        withContext(Dispatchers.IO) {
            api.getBusketPending(status).map {
                it.map { list ->
                    busketListMapper.map(list)
                }
            }
        }

    override suspend fun getBusketProducts(id: Int): Outcome<List<BusketProducts>> =
        withContext(Dispatchers.IO) {
            api.getBusketProducts(id).map{
                it.map { products ->
                    busketProductsMapper.map(products)
                }
            }
        }

    override suspend fun removeFood(id: Int): Outcome<Unit> = withContext(Dispatchers.IO){
        api.removeFood(id)
    }

    override suspend fun changeStatus(id: Int, status: Int): Outcome<Unit> = withContext(Dispatchers.IO) {
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPartWithSkippingNull("status", status.toString())
            .build()
        api.changeStatus(id, requestBody)
    }

    override suspend fun foodAdd(id: Int, quantity: Int): Outcome<Unit> = withContext(Dispatchers.IO) {
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPartWithSkippingNull("food", id.toString())
            .addFormDataPartWithSkippingNull("quantity", quantity.toString())
            .build()
        api.foodAdd(requestBody)
    }

    override suspend fun getProfile(): Outcome<UserProfile> = withContext(Dispatchers.IO) {
        api.getUserProfile().map{
            UserProfile(
                id = it.id,
                username = it.username.orEmpty(),
                email = it.email.orEmpty(),
                grade = it.grade.orEmpty(),
                address = it.address.orEmpty(),
                aboutYourself = it.aboutYourself.orEmpty(),
                phoneNumber = it.phoneNumber.orEmpty(),
                avatar = it.avatar.orEmpty(),
                firstName = it.firstName.orEmpty(),
                lastName = it.lastName.orEmpty(),
                middleName = it.middleName.orEmpty(),
            )
        }
    }
}