package kz.rdd.profile.presentation.order

import kz.rdd.busket.domain.BusketUseCase
import kz.rdd.busket.domain.model.BusketList
import kz.rdd.busket.domain.model.BusketProducts
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.utils.outcome.doOnSuccess

data class OrderState(
    val products: List<BusketProducts> = emptyList(),
    val list: List<BusketList> = emptyList(),
) : UiState
class OrderViewModel(
    private val busketuseCase: BusketUseCase,
) : BaseViewModel<OrderState>() {
    override fun createInitialState() = OrderState()

    init {
        load()
    }
    fun load() {
        launch {
            busketuseCase.getBusketList(2)
                .doOnSuccess {
                    if(it.isEmpty()){
                        setState {
                            copy(
                                products = emptyList(),
                            )
                        }
                        return@doOnSuccess
                    }
                    setState {
                        copy(
                            list = it
                        )
                    }
                }
        }
    }
}