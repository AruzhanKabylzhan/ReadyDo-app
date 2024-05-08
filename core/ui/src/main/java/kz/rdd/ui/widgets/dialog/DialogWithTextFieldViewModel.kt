package kz.rdd.core.ui.widgets.dialog

import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.base.viewmodel.handleError
import kz.rdd.core.ui.screen.CommonTextFieldDialogDestination
import kz.rdd.core.utils.outcome.doOnError
import kz.rdd.core.utils.outcome.doOnSuccess

internal data class DialogTextFieldState(
    val loading: Boolean = false,
    val field: String = "",
    val isEnabled: Boolean,
) : UiState

internal class DialogWithTextFieldViewModel(
    private val behavior: CommonTextFieldDialogDestination.Behavior,
) : BaseViewModel<DialogTextFieldState>() {
    override fun createInitialState() = DialogTextFieldState(
        isEnabled = behavior.canBeEmpty
    )

    fun onClickButton() {
        launch {
            setState {
                copy(loading = true)
            }
            behavior.onClickButton(currentState.field)
                .doOnSuccess {
                    setState {
                        copy(loading = false)
                    }
                }
                .doOnError {
                    setState {
                        copy(loading = false)
                    }
                    handleError(it)
                }
        }
    }

    fun onUpdateTextField(value: String) {
        setState {
            copy(
                field = value,
                isEnabled = if (!behavior.canBeEmpty) {
                    value.isNotEmpty()
                } else {
                    true
                }
            )
        }
    }
}
