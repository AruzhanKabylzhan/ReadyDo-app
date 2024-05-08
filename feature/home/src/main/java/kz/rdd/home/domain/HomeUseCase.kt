package kz.rdd.home.domain

import kz.rdd.home.domain.repo.HomeRepo

class HomeUseCase(
    private val repo: HomeRepo,
) {
    suspend fun getChefs() = repo.getChefs()
}