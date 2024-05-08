package kz.rdd.busket.presentation.pay

import kz.rdd.busket.domain.BusketUseCase
import kz.rdd.core.ui.base.navigation.NavigateTo
import kz.rdd.core.ui.base.navigation.PopUpTo
import kz.rdd.core.ui.base.navigation.ShowSheet
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.utils.outcome.doOnSuccess
import kz.rdd.store.UserCard
import kz.rdd.store.UserStore
import okhttp3.internal.userAgent

data class PaySheetState(
    val cards: List<UserCard> = emptyList(),
    val selectedCard: UserCard? = null,
) : UiState

class PaySheetViewModel(
    private val userStore: UserStore,
    private val busketId: Int,
    private val busketUseCase: BusketUseCase,
) : BaseViewModel<PaySheetState>() {
    override fun createInitialState() = PaySheetState()

    init {
        load()
    }

    fun load() {
        println(userStore.userCards)
        setState { copy(cards = userStore.userCards.orEmpty()) }
    }

    fun onClickCard(userCard: UserCard) {
        setState {
            copy(
                selectedCard = userCard
            )
        }
    }

    fun onClickAddCard() {
        navigate(ShowSheet(AddCardSheetDestination()))
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