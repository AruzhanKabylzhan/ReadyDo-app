package kz.rdd.home.presentation.best

import kz.rdd.catalog.domain.CatalogUseCase
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.utils.outcome.doOnSuccess
import kz.rdd.home.domain.HomeUseCase

data class Bests(
    val id: Int,
    val image: String,
    val username: String,
    val fullName: String,
    val stars: Int,
)

data class BestState(
    val isChefs: Boolean = false,
    val bestList: List<Bests> = emptyList()
) : UiState

class BestViewModel(
    private val params: BestParams,
    private val homeUseCase: HomeUseCase,
    private val catalogUseCase: CatalogUseCase
) : BaseViewModel<BestState>() {
    override fun createInitialState() = BestState(
        isChefs = params.isChefs,
    )

    init {
        load()
    }

    private fun load() {
        if (currentState.isChefs) {
            launch {
                homeUseCase.getChefs()
                    .doOnSuccess {
                        val list = it.filter { item -> item.grade.toDouble().toInt() > 4 }
                        setState {
                            copy(
                                bestList = list.map {
                                    Bests(
                                        id = it.id,
                                        image = it.avatar.orEmpty(),
                                        username = it.username,
                                        fullName = it.lastName.plus(" ").plus(it.firstName),
                                        stars = it.grade.toDouble().toInt(),
                                    )
                                }
                            )
                        }
                    }
            }
        } else {
            launch {
                catalogUseCase.getFoods(null, null, null, null, null)
                    .doOnSuccess {
                        val list = it.filter { item -> item.grade.toDouble().toInt() > 3 }
                        setState {
                            copy(
                                bestList = list.map {
                                    Bests(
                                        id = it.id,
                                        image = it.photo,
                                        username = it.name,
                                        fullName = it.username,
                                        stars = it.grade.toDouble().toInt(),
                                    )
                                }
                            )
                        }
                    }
            }
        }
    }
}