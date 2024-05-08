package kz.rdd.busket.presentation

import kz.rdd.busket.domain.BusketUseCase
import kz.rdd.busket.domain.model.BusketProducts
import kz.rdd.busket.presentation.pay.AddCardSheetDestination
import kz.rdd.busket.presentation.pay.PayDestination
import kz.rdd.busket.presentation.pay.PayScreen
import kz.rdd.busket.presentation.pay.PaySheetDestination
import kz.rdd.busket.presentation.pay.PaySheetViewModel
import kz.rdd.core.ui.base.effect.SuccessEffect
import kz.rdd.core.ui.base.navigation.NavigateTo
import kz.rdd.core.ui.base.navigation.ShowSheet
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.base.viewmodel.handleError
import kz.rdd.core.utils.outcome.doOnError
import kz.rdd.core.utils.outcome.doOnSuccess

data class Product(
    val id: Int,
    val image: String,
    val username: String,
    val title: String,
    val price: Int,
)
data class BusketState(
    val products: List<BusketProducts> = emptyList(),
    val total: Int = 0,
    val busketId: Int = 0,
) : UiState
class BusketViewModel(
    private val busketuseCase: BusketUseCase
) : BaseViewModel<BusketState>() {
    override fun createInitialState() = BusketState()

    init {
        load()
    }

    fun onClickDelete(ids: Int){
        launch {
            busketuseCase.removeFood(ids)
            load()
            setEffect { SuccessEffect() }
        }
    }

    fun loadProducts(id: Int){
        launch {
            println("YES")
            busketuseCase.getBusketProducts(id)
                .doOnSuccess {
                    setState {
                        copy(
                            products = it
                        )
                    }
                }
        }
    }

    fun load() {
        launch {
            busketuseCase.getBusketList(1)
                .doOnSuccess {
                    if(it.isEmpty()){
                        setState {
                            copy(
                                products = emptyList(),
                                total = 0,
                                busketId = 0,
                            )
                        }
                        return@doOnSuccess
                    }
                    setState {
                        copy(
                            total = it[0].totalPrice,
                            busketId = it[0].id
                        )
                    }
                    loadProducts(it[0].id)
                }
        }
    }

    fun onClickPay() {
        navigate(NavigateTo(PayDestination(currentState.busketId, currentState.total)))
    }
}