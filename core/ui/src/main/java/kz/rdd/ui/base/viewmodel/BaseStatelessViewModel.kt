package kz.rdd.core.ui.base.viewmodel

object NoState : UiState

abstract class BaseStatelessViewModel : BaseViewModel<UiState>() {
    override fun createInitialState(): UiState = NoState
}
