package kz.rdd.core.ui.base.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.navigation.NavigateBack
import kz.rdd.core.ui.base.navigation.NavigationEvent
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel<State : UiState> : ViewModel(),
    BaseViewModelHandler.StateInitializer<State> {

    private val handler: BaseViewModelHandler<State> by lazy {
        BaseViewModelHandlerImpl(
            stateInitializer = ::createInitialState,
            scope = viewModelScope
        )
    }

    val currentState get() = handler.currentState

    val uiState: StateFlow<State> get() = handler.uiState
    val event: Flow<UiEvent> get() = handler.event
    val effect: Flow<UiEffect> get() = handler.effect

    fun setEvent(event: UiEvent) = handler.setEvent(event)
    fun setState(reduce: State.() -> State) = handler.setState(reduce)
    fun setEffect(builder: () -> UiEffect) = handler.setEffect(builder)

    fun navigate(event: NavigationEvent) = handler.setEvent(event)

    open fun navigateBack() = handler.setEvent(NavigateBack)

    fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        coroutineExceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, t ->
            sendErrorMessage(R.string.common_error_unknown)
            Timber.d(t, "Error when loading something")
        },
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(
        context = coroutineExceptionHandler + context,
        start = start,
        block = block,
    )
}

interface BaseViewModelHandler<State : UiState> {

    val currentState: State get() = uiState.value

    val uiState: StateFlow<State>
    val event: Flow<UiEvent>
    val effect: Flow<UiEffect>

    fun setEvent(event: UiEvent)
    fun setState(reduce: State.() -> State)
    fun setEffect(builder: () -> UiEffect)

    fun interface StateInitializer<State : UiState> {
        fun createInitialState(): State
    }
}

open class BaseViewModelHandlerImpl<State : UiState>(
    private val stateInitializer: BaseViewModelHandler.StateInitializer<State>,
    private val scope: CoroutineScope
) : BaseViewModelHandler<State> {

    private val initialState: State by lazy { stateInitializer.createInitialState() }

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    override val uiState = _uiState.asStateFlow()

    private val _event: Channel<UiEvent> = Channel(Channel.BUFFERED)
    override val event = _event.receiveAsFlow().shareIn(scope, SharingStarted.Lazily)

    private val _effect: Channel<UiEffect> = Channel(Channel.BUFFERED)
    override val effect = _effect.receiveAsFlow().shareIn(scope, SharingStarted.Lazily)

    override fun setEvent(event: UiEvent) {
        val newEvent = event
        scope.launch { _event.send(newEvent) }
    }

    override fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.update { newState }
    }

    override fun setEffect(builder: () -> UiEffect) {
        val effectValue = builder()
        scope.launch { _effect.send(effectValue) }
    }

}

@Stable
interface UiState
interface UiEffect
interface UiEvent
