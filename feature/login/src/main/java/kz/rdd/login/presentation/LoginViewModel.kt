package kz.rdd.login.presentation

import androidx.compose.ui.graphics.painter.Painter
import kz.rdd.core.ui.base.navigation.NavigateTo
import kz.rdd.core.ui.base.navigation.NavigateToMain
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.base.viewmodel.handleError
import kz.rdd.core.utils.outcome.doOnError
import kz.rdd.core.utils.outcome.doOnSuccess
import kz.rdd.core.ui.R
import kz.rdd.login.domain.AuthUseCase
import kz.rdd.login.presentation.forgot_password.ForgotPasswordDestination
import kz.rdd.login.presentation.registration.RegistrationDestination
import kz.rdd.store.UserStore

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isIntroduction: Boolean = false,
    val image: Int = 0,
    val title: String = "",
    val description: String = "",
    val index: Int = 1,
) : UiState

class LoginViewModel(
    private val authUseCase: AuthUseCase,
    val userStore: UserStore,
) : BaseViewModel<LoginState>() {

    override fun createInitialState() = LoginState(
        //isIntroduction = userStore.isGotAcquainted,
    )

    fun onClickSkip() {
        userStore.isGotAcquainted = true
        setState {
            copy(
                isIntroduction = true
            )
        }
    }

    fun onUpdateEmail(item: String){
        setState { copy(email = item) }
    }

    fun onUpdatePassword(item: String){
        setState { copy(password = item) }
    }

    fun onClickProceed(){
        launch {
            authUseCase.getToken(
                email = currentState.email.trim(),
                password = currentState.password,
            ).doOnSuccess {
                navigate(NavigateToMain(replace = true))
            }.doOnError {
                handleError(it)
            }
        }
    }

    fun onClickRegistration(){
        navigate(NavigateTo(RegistrationDestination()))
    }

    fun onClickForgotPassword(){
        navigate(NavigateTo(ForgotPasswordDestination()))
    }
}