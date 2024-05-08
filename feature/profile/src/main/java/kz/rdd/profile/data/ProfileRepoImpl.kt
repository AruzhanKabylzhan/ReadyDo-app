package kz.rdd.profile.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.rdd.core.utils.ext.addFormDataPartWithSkippingNull
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.core.utils.outcome.map
import kz.rdd.profile.data.model.RequestProfile
import kz.rdd.profile.data.network.ChangePass
import kz.rdd.profile.data.network.ProfileApi
import kz.rdd.profile.data.network.Send
import kz.rdd.profile.data.network.SendResponse
import kz.rdd.profile.domain.model.FavList
import kz.rdd.profile.domain.model.UserProfile
import kz.rdd.profile.domain.repo.ProfileRepo
import kz.rdd.store.UserStore
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ProfileRepoImpl(
    private val profileApi: ProfileApi,
    private val userStore: UserStore,
) : ProfileRepo {
    override suspend fun getProfile(): Outcome<UserProfile> = withContext(Dispatchers.IO) {
        profileApi.getUserProfile().map{
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

    override suspend fun changeProfile(profile: RequestProfile): Outcome<Unit> = withContext(Dispatchers.IO) {
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPartWithSkippingNull("first_name", profile.firstName)
            .addFormDataPartWithSkippingNull("last_name", profile.lastName)
            .addFormDataPartWithSkippingNull("address", profile.address)
            .addFormDataPartWithSkippingNull("about_yourself", profile.aboutMySelf)
            .addFormDataPartWithSkippingNull("phone_number", profile.phoneNumber)
            .apply {
                if(profile.avatar != null){
                    addFormDataPartWithSkippingNull("avatar", profile.avatar.let { File(it) })
                }
            }
            .build()
        profileApi.changeProfile(requestBody)
    }

    override suspend fun send(): Outcome<SendResponse> = withContext(Dispatchers.IO) {
        profileApi.send(Send(userStore.userDetails?.email.orEmpty())).map { SendResponse(it.token) }
    }

    override suspend fun changePass(password: String, token: String): Outcome<Unit> = withContext(Dispatchers.IO) {
        profileApi.changePass(ChangePass(password, "0000", token))
    }

    override suspend fun getFavList(): Outcome<List<FavList>> = withContext(Dispatchers.IO) {
        profileApi.getFavList().map {
            it.map {
                FavList(
                    id = it.id,
                    food = FavList.Food(
                        id = it.food.id,
                        name = it.food.name.orEmpty(),
                        price = it.food.price,
                        ingredients = it.food.ingredients.orEmpty(),
                        cuisine = it.food.cuisine,
                        taste = it.food.taste,
                        grade = it.food.grade.orEmpty(),
                        photo = it.food.photo.orEmpty(),
                        username = it.food.username
                    )
                )
            }
        }
    }

    override suspend fun deleteFavProduct(id: Int): Outcome<Unit> = withContext(Dispatchers.IO) {
        profileApi.deleteFavProduct(id)
    }
}