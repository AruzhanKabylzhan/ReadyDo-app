package kz.rdd.profile.domain

import kz.rdd.profile.data.model.RequestProfile
import kz.rdd.profile.domain.repo.ProfileRepo

class ProfileUseCase(
    private val repo: ProfileRepo,
) {
    suspend fun getProfile() = repo.getProfile()

    suspend fun changeProfile(profile: RequestProfile) = repo.changeProfile(profile)

    suspend fun send() = repo.send()

    suspend fun changePass(password: String, token: String) = repo.changePass(password, token)
}