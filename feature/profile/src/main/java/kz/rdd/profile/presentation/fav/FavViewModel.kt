package kz.rdd.profile.presentation.fav

import kz.rdd.busket.domain.BusketUseCase
import kz.rdd.core.ui.base.effect.ToastEffect
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.base.viewmodel.handleError
import kz.rdd.core.ui.utils.VmRes
import kz.rdd.core.utils.outcome.doOnError
import kz.rdd.core.utils.outcome.doOnSuccess
import kz.rdd.profile.domain.model.FavList
import kz.rdd.profile.domain.repo.ProfileRepo

data class FavState(
    val list: List<FavList> = emptyList(),
) : UiState

class FavViewModel(
    private val profileRepo: ProfileRepo,
    private val busketUseCase: BusketUseCase,
) : BaseViewModel<FavState>() {
    override fun createInitialState() = FavState()

    init {
        load()
    }

    private fun load() {
        launch {
            profileRepo.getFavList()
                .doOnSuccess {
                    setState {
                        copy(
                            list = it
                        )
                    }
                }.doOnError {
                    handleError(it)
                }
        }
    }

    fun onClickBuy(i: Int) {
        launch {
            busketUseCase.foodAdd(i, 1)
                .doOnSuccess {
                    setEffect { ToastEffect(VmRes.Str("Food add")) }
                }.doOnError {
                    handleError(it)
                }
        }
    }

    fun onClickDeleteFav(id: Int) {
        launch {
            profileRepo.deleteFavProduct(id)
                .doOnSuccess {
                    setEffect { ToastEffect(VmRes.Str("Food deleted")) }
                    load()
                }
        }
    }
}