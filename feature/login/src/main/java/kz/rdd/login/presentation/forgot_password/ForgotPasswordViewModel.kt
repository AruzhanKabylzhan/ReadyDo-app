package kz.rdd.login.presentation.forgot_password

import kz.rdd.core.ui.base.navigation.NavigateTo
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.base.viewmodel.handleError
import kz.rdd.core.utils.outcome.doOnError
import kz.rdd.core.utils.outcome.doOnSuccess
import kz.rdd.login.domain.AuthUseCase
import kz.rdd.login.domain.model.SendSmsRequestData
import kz.rdd.login.presentation.otp.OtpDestination
import kz.rdd.login.presentation.otp.OtpParams

data class ForgotPasswordState(
    val email: String = "",
) : UiState
class ForgotPasswordViewModel(
    private val authUseCase: AuthUseCase
) : BaseViewModel<ForgotPasswordState>() {

    override fun createInitialState() = ForgotPasswordState()

    fun onUpdateEmail(email: String){
        setState { copy(email = email) }
    }

    fun onClickProceed(){
        launch {
            authUseCase.sendCode(
                SendSmsRequestData(
                    email = currentState.email
                )
            ).doOnSuccess {
                navigate(NavigateTo(OtpDestination(OtpParams(it.token.orEmpty()))))
            }.doOnError {
                handleError(it)
            }
        }

    }
}