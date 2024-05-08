package kz.rdd.login.presentation.registration

import android.net.Uri
import kz.rdd.core.local_storage.domain.LocalStorage
import kz.rdd.core.ui.base.effect.SuccessEffect
import kz.rdd.core.ui.base.navigation.PopUpTo
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.base.viewmodel.handleError
import kz.rdd.core.utils.outcome.doOnError
import kz.rdd.core.utils.outcome.doOnSuccess
import kz.rdd.login.domain.AuthUseCase
import kz.rdd.login.domain.model.RegisterEmployeeRequest

data class RegistrationParams(
    val otpToken: String,
)

data class RegistrationState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val selectedAvatar: Uri? = null,
    val firstName: String = "",
    val lastName: String = "",
    val username: String = "",
    val phoneNumber: String = "",
    val grade: String = "",
    val address: String = "",
    val yourself: String = "",
) : UiState

class RegistrationViewModel(
    private val authUseCase: AuthUseCase,
    private val localStorage: LocalStorage,
) : BaseViewModel<RegistrationState>() {

    override fun createInitialState() = RegistrationState()

    fun onUpdateEmail(item: String){
        setState { copy(email = item) }
    }

    fun onUpdatePassword(item: String){
        setState { copy(password = item) }
    }

    fun onUpdateRepeatPassword(item: String){
        setState { copy(repeatPassword = item) }
    }

    fun onUpdateFirstName(item: String){
        setState { copy(firstName = item) }
    }

    fun onUpdateAddressName(item: String){
        setState { copy(address = item) }
    }

    fun onUpdateGradeName(item: String){
        setState { copy(grade = item) }
    }

    fun onUpdateYourselfName(item: String){
        setState { copy(yourself = item) }
    }

    fun onUpdateLastName(item: String){
        setState { copy(lastName = item) }
    }

    fun onUpdateUsername(item: String){
        setState { copy(username = item) }
    }

    fun onUpdatePhoneNumber(item: String){
        setState { copy(phoneNumber = item) }
    }

    fun setAvatar(uri: Uri){
        setState {
            copy(
                selectedAvatar = uri
            )
        }
    }

    fun onClickProceed(){
        launch {
            val avatarFilePath = currentState.selectedAvatar?.let {
                localStorage.saveExternalFile(uri = it).first
            }
            authUseCase.getRegisterEmployee(
                RegisterEmployeeRequest(
                    avatar = avatarFilePath,
                    password = currentState.password,
                    firstName = currentState.firstName,
                    lastName = currentState.lastName,
                    middleName = "",
                    email = currentState.email,
                    phoneNumber = currentState.phoneNumber,
                    grade = currentState.grade,
                    address = currentState.address,
                    yourself = currentState.yourself,
                    username = currentState.username
                )
            ).doOnError {
                handleError(it)
            }.doOnSuccess {
                setEffect { SuccessEffect() }
                navigate(PopUpTo { it.isLogin })
            }
        }
    }
}