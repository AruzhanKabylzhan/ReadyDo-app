package kz.rdd.busket.presentation.pay

import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.store.UserCard
import kz.rdd.store.UserStore

data class AddCardState(
    val cardNum: String = "",
    val date: String = "",
    val cvv: String = "",
) : UiState

class AddCardViewModel(
    private val userStore: UserStore
) : BaseViewModel<AddCardState>() {
    override fun createInitialState() = AddCardState()

    fun onUpdateCardNum(str: String) {
        setState { copy(cardNum = str) }
    }

    fun onUpdateDate(str: String) {
        setState { copy(date = str) }
    }

    fun onUpdateCvv(str: String) {
        setState { copy(cvv = str) }
    }

    fun onClickLink() {
        launch {
            userStore.userCards = userStore.userCards.orEmpty() + listOf(
                UserCard(
                    currentState.cardNum.toLong(),
                    currentState.date,
                    currentState.cvv.toInt()
                )
            )
            navigateBack()
        }
    }
}