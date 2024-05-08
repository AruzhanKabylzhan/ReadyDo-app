package kz.rdd.busket.presentation.pay

import kz.rdd.busket.domain.BusketUseCase
import kz.rdd.core.ui.base.navigation.PopUpTo
import kz.rdd.core.ui.base.navigation.ShowSheet
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.utils.outcome.doOnSuccess

data class PayState(
    val total: Int = 0,
    val address: String = "",
    val phone: String = "",
    val comment: String = "",
    val method: Int = 2,
) : UiState

class PayViewModel(
    private val busketId: Int,
    private val total: Int,
    private val busketUseCase: BusketUseCase,
) : BaseViewModel<PayState>() {
    override fun createInitialState() = PayState(
        total = total
    )

    init{
        load()
    }

    private fun load() {
        launch {
            busketUseCase.getProfile()
                .doOnSuccess {
                    setState {
                        copy(
                            address = it.address,
                            phone = it.phoneNumber,
                        )
                    }
                }
        }
    }

    fun onUpdateComment(str: String) {
        setState {
            copy(
                comment = str
            )
        }
    }

    fun onChangeMethod(id: Int) {
        setState {
            copy(
                method = id
            )
        }
    }

    fun onClickPay() {
        launch {
            busketUseCase.changeStatus(busketId, 2)
                .doOnSuccess {
                    navigate(PopUpTo { it.isMain })
                }
        }
    }
}