package kz.rdd.profile.presentation.edit

import kz.rdd.core.ui.base.effect.SuccessEffect
import kz.rdd.core.ui.base.navigation.PopUpTo
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.base.viewmodel.handleError
import kz.rdd.core.utils.outcome.doOnError
import kz.rdd.core.utils.outcome.doOnSuccess
import kz.rdd.profile.domain.ProfileUseCase

data class ChangePasswordState(
    val password: String = "",
    val password2: String = "",
) : UiState
class ChangePasswordViewModel(
    private val profileUseCase: ProfileUseCase
) : BaseViewModel<ChangePasswordState>() {
    override fun createInitialState() = ChangePasswordState()
    fun onUpdatePassword(s: String) {
        setState { copy(password = s) }
    }

    fun onUpdatePassword2(s: String) {
        setState { copy(password2 = s) }
    }

    fun onClickProceed() {
        launch {
            profileUseCase.send()
                .doOnSuccess {
                    profileUseCase.changePass(currentState.password, it.token)
                        .doOnSuccess {
                            setEffect { SuccessEffect() }
                            navigateBack()
                            navigate(PopUpTo{it.isMain})
                        }.doOnError {
                            handleError(it)
                        }
                }.doOnError {
                    handleError(it)
                }
        }
    }
}