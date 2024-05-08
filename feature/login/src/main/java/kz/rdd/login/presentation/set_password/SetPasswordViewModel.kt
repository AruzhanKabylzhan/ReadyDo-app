package kz.rdd.login.presentation.set_password

import kz.rdd.core.ui.base.effect.SuccessEffect
import kz.rdd.core.ui.base.navigation.PopUpTo
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.base.viewmodel.handleError
import kz.rdd.core.utils.outcome.doOnError
import kz.rdd.core.utils.outcome.doOnSuccess
import kz.rdd.login.domain.AuthUseCase

data class SetPasswordState(
    val password: String = "",
    val repeatPassword: String = "",
) : UiState

class SetPasswordViewModel(
    private val authUseCase: AuthUseCase,
    private val code: String,
    private val token: String,
) : BaseViewModel<SetPasswordState>() {
    override fun createInitialState() = SetPasswordState()

    fun onUpdatePassword(password: String) {
        setState { copy(password = password) }
    }

    fun onUpdateRepeatPassword(password: String) {
        setState { copy(repeatPassword = password) }
    }

    fun onClickProceed() {
        launch {
            authUseCase.verifySms(
                password = currentState.password,
                code = code,
                token = token
            ).doOnSuccess {
                navigate(PopUpTo { it.isLogin })
            }.doOnError {
                handleError(it)
            }
        }
    }
}