package kz.rdd.core.ui.screen.loading_indicator

import kotlinx.coroutines.CoroutineExceptionHandler
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.base.viewmodel.handleError
import kz.rdd.core.utils.outcome.Outcome

internal data class LoadingIndicatorState(
    val progress: Float
) : UiState

internal class LoadingIndicatorViewModel(
    private val behavior: LoadingIndicatorDialogDestination.Behavior,
) : BaseViewModel<LoadingIndicatorState>() {
    override fun createInitialState() = LoadingIndicatorState(
        progress = 0f
    )

    init {
        launch(CoroutineExceptionHandler { _, _ ->
            behavior.onError(::setEvent)
            handleError(Outcome.Error.ConnectionError)
        }) {
            behavior.load(
                setProgress = {
                    setState {
                        copy(progress = it)
                    }
                },
                onSuccessEvent = ::setEvent,
            )
        }
    }
}
