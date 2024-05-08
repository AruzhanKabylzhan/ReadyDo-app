package kz.rdd.profile.domain.repo

import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.profile.data.model.RequestProfile
import kz.rdd.profile.data.network.SendResponse
import kz.rdd.profile.domain.model.FavList
import kz.rdd.profile.domain.model.UserProfile

interface ProfileRepo {
    suspend fun getProfile() : Outcome<UserProfile>

    suspend fun changeProfile(profile: RequestProfile) : Outcome<Unit>

    suspend fun send() : Outcome<SendResponse>
    suspend fun changePass(password: String, token: String) : Outcome<Unit>

    suspend fun getFavList() : Outcome<List<FavList>>
    suspend fun deleteFavProduct(id: Int): Outcome<Unit>
}