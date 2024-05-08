package kz.rdd.main.start

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import dev.olshevski.navigation.reimagined.Match
import dev.olshevski.navigation.reimagined.NavAction
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.navController
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.pop
import dev.olshevski.navigation.reimagined.popAll
import dev.olshevski.navigation.reimagined.popUpTo
import dev.olshevski.navigation.reimagined.replaceLast
import dev.olshevski.navigation.reimagined.replaceUpTo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.base.AcceptResult
import kz.rdd.core.ui.base.DialogDestination
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.base.Screen
import kz.rdd.core.ui.base.SheetDestination
import kz.rdd.core.ui.base.navigation.NavigationEvent
import kz.rdd.core.utils.Language
import kz.rdd.core.utils.fromLocale
import kz.rdd.store.UserStore
import java.util.Locale

internal class GlobalViewModel(
    savedStateHandle: SavedStateHandle,
    val userStore: UserStore,
) : ViewModel(), DestinationController {

    @OptIn(SavedStateHandleSaveableApi::class)
    override val navController by savedStateHandle.saveable<NavController<Destination>> {
        navController(getStartDestination())
    }

    @OptIn(SavedStateHandleSaveableApi::class)
    override val sheetNavController by savedStateHandle.saveable<NavController<SheetDestination>> {
        navController(initialBackstack = emptyList())
    }

    @OptIn(SavedStateHandleSaveableApi::class)
    override val dialogNavController by savedStateHandle.saveable<NavController<DialogDestination>> {
        navController(initialBackstack = emptyList())
    }

    private val _pendingNavigationEvent: Channel<NavigationEvent> = Channel(Channel.BUFFERED)
    override val pendingNavigationEvent = _pendingNavigationEvent
        .receiveAsFlow()
        .shareIn(viewModelScope, SharingStarted.Lazily)

    init {
        observeLoginTokenState()
    }

    override fun navigateToLogin(replace: Boolean) {
        popAll()
        navigate(
            replace = replace,
            destination = StartDestination.Login
        )
    }

    override fun navigateToMain(replace: Boolean) {
        navigate(
            replace = replace,
            destination = StartDestination.Main()
        )
    }

    override fun navigateToLoading(replace: Boolean) {
        navigate(
            replace = replace,
            destination = StartDestination.LoadingUserDetails
        )
    }

    override fun navigateToDestination(replace: Boolean, destination: Destination) {
        navigate(
            replace = replace,
            destination = destination
        )
    }

    override fun replaceUpTo(
        destination: Destination,
        inclusive: Boolean,
        matchLast: Boolean,
        predicate: (Destination) -> Boolean
    ) {
        navController.replaceUpTo(
            newDestination = destination,
            inclusive = inclusive,
            match = if (matchLast) Match.Last else Match.First,
            predicate = predicate,
        )
    }

    override fun navigateBack() {
        if (dialogNavController.pop()) return
        if (sheetNavController.pop()) return
        navController.pop()
    }

    override fun closeOverlayNavigations() {
        sheetNavController.popAll()
        dialogNavController.popAll()
    }

    override fun popUpTo(
        inclusive: Boolean,
        matchLast: Boolean,
        predicate: (Destination) -> Boolean
    ) {
        navController.popUpTo(
            inclusive = inclusive,
            match = if (matchLast) Match.Last else Match.First,
            predicate = predicate,
        )
    }

    override fun removeDestinationFromLast(predicate: (Screen) -> Boolean) {
        if (dialogNavController.removeDestinationFromLast(predicate)) return
        if (sheetNavController.removeDestinationFromLast(predicate)) return
        navController.removeDestinationFromLast(predicate)
    }

    override fun showSheet(destination: SheetDestination) {
        sheetNavController.navigate(destination)
    }

    override fun showDialog(destination: DialogDestination, replace: Boolean) {
        if (replace) {
            dialogNavController.replaceLast(destination)
            return
        }
        dialogNavController.navigate(destination)
    }

    override fun setPendingNavigationEvent(event: NavigationEvent) {
        viewModelScope.launch {
            _pendingNavigationEvent.send(event)
        }
    }

    fun keepScreenWhenCondition(): Boolean {
        return false
    }

    fun onDismissSheet() {
        sheetNavController.backstack.entries.lastOrNull()?.let {
            if (it.destination.sendDefaultDismissedResult) {
                navController.backstack.entries.lastOrNull()?.let { navEntry ->
                    viewModelScope.launch {
                        (navEntry.destination as? AcceptResult)?.setResult(
                            AcceptResult.Data(
                                key = SheetDestination.ACCEPT_RESULT_DISMISSED,
                                value = it.destination.tag,
                            )
                        )
                    }
                }
            }
            if (it.destination.dismissByTouch) {
                it.destination.onDismiss(this)
                sheetNavController.pop()
            }
        } ?: run {
            sheetNavController.pop()
        }
    }

    fun onDismissDialog() {
        dialogNavController.backstack.entries.lastOrNull()?.let {
            if (it.destination.dismissByTouch) {
                it.destination.onDismiss(this, viewModelScope)
                dialogNavController.pop()
            }
        } ?: run {
            dialogNavController.pop()
        }
    }

    private fun getStartDestination() = if (userStore.isLogged) {
        StartDestination.LoadingUserDetails
    } else {
        StartDestination.Login
    }

    private fun observeLoginTokenState() {
        viewModelScope.launch(CoroutineExceptionHandler { _, _ -> }) {
            userStore.isLoggedFlow.collectLatest { isLogged ->
                if (!isLogged) navigateToLogin(replace = false)
            }
        }
    }

    private fun navigate(replace: Boolean, destination: Destination) {
        if (replace) {
            navController.replaceLast(destination)
            return
        }
        navController.navigate(destination)
    }

    private fun popAll() {
        navController.popAll()
        sheetNavController.popAll()
        dialogNavController.popAll()
    }

    fun setLocaleChanged(locale: Locale) {
        userStore.language = Language.fromLocale(locale)
        Locale.setDefault(locale)
    }

    private inline fun <T> NavController<T>.removeDestinationFromLast(
        predicate: (T) -> Boolean
    ): Boolean =
        if (backstack.entries.isNotEmpty()) {
            val indexOfLast = backstack.entries.indexOfLast { predicate(it.destination) }
            if (indexOfLast != -1) {
                setNewBackstack(
                    entries = backstack.entries.toMutableList().apply {
                        removeAt(indexOfLast)
                    },
                    action = NavAction.Pop
                )
                true
            } else {
                false
            }
        } else {
            false
        }
}
