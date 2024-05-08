package kz.rdd.login.presentation.otp

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.base.navigation.NavigateTo
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.base.viewmodel.handleError
import kz.rdd.core.ui.ext.getError
import kz.rdd.core.ui.utils.VmRes
import kz.rdd.core.utils.ext.onlyDigits
import kz.rdd.core.utils.outcome.doOnError
import kz.rdd.core.utils.outcome.doOnSuccess
import kz.rdd.login.domain.AuthUseCase
import kz.rdd.login.domain.model.SendSmsRequestData
import kz.rdd.login.presentation.set_password.SetPasswordDestination

@Immutable
internal data class State(
    val otpValue: String = "",
    val errorText: VmRes.Parcelable<CharSequence>? = null,
    val counter: Int,
    val isLoading: Boolean = false,
    val currentToken: String = "",
) : UiState {
    val canProceed = otpValue.length == 4
}

@Parcelize
class OtpParams(
    val token: String,
) : Parcelable

internal class OtpViewModel(
    private val params: OtpParams,
    private val authUseCase: AuthUseCase,
) : BaseViewModel<State>() {

    override fun createInitialState() = State(
        counter = COUNTER_INITIAL_SEC,
    )

    init {
        startCounting()
    }

    fun onUpdateOtp(value: String, end: Boolean) {
        if (currentState.isLoading) return
        setState {
            copy(otpValue = value.onlyDigits(), errorText = null)
        }
    }

    private fun startCounting() {
        if (currentState.isLoading) return
        val from = COUNTER_INITIAL_SEC
        val to = 0
        launch(Dispatchers.Default) {
            for (count in (from downTo to)) {
                updateOtpCounter(count)
                if (count != 0) delay(1000L)
            }
        }
    }

    fun onClickContinue() {
        verifySms()
    }

    private fun verifySms() {
        launch {
            setState { copy(isLoading = true) }
            authUseCase.verifySms(
                password = "0000",
                code = currentState.otpValue,
                token = params.token,
            ).doOnSuccess {
                navigate(NavigateTo(SetPasswordDestination(currentState.otpValue, params.token)))
            }.doOnError {
                setState { copy(errorText = it.getError()) }
            }

            setState { copy(isLoading = false) }
        }
    }

    private fun updateOtpCounter(counter: Int) {
        setState { copy(counter = counter) }
    }

    private companion object {
        private const val COUNTER_INITIAL_SEC = 60
    }
}
