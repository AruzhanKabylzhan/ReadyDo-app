package kz.rdd.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import dev.olshevski.navigation.reimagined.NavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.rdd.core.ui.base.DialogDestination
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.base.Screen
import kz.rdd.core.ui.base.SheetDestination
import kz.rdd.core.ui.base.navigation.NavigationEvent
import kz.rdd.core.ui.base.viewmodel.UiEvent
import kotlin.properties.Delegates

@Stable
interface DestinationController {
    val navController: NavController<Destination>
    val sheetNavController: NavController<SheetDestination>
    val dialogNavController: NavController<DialogDestination>

    val pendingNavigationEvent: Flow<NavigationEvent>

    fun navigateToLogin(replace: Boolean = true)
    fun navigateToMain(
        replace: Boolean = false,
    )
    fun navigateToLoading(replace: Boolean = false)
    fun navigateToDestination(replace: Boolean = false, destination: Destination)
    fun replaceUpTo(
        destination: Destination,
        inclusive: Boolean = false,
        matchLast: Boolean = true,
        predicate: (Destination) -> Boolean,
    )
    fun navigateBack()
    fun closeOverlayNavigations()
    fun popUpTo(
        inclusive: Boolean = false,
        matchLast: Boolean = true,
        predicate: (Destination) -> Boolean
    )
    fun removeDestinationFromLast(predicate: (Screen) -> Boolean)

    fun showSheet(destination: SheetDestination)

    fun showDialog(destination: DialogDestination, replace: Boolean = false)
    
    fun setPendingNavigationEvent(event: NavigationEvent)
}

class MockDestinationController : DestinationController {
    override val navController: NavController<Destination> by Delegates.notNull()
    override val sheetNavController: NavController<SheetDestination> by Delegates.notNull()
    override val dialogNavController: NavController<DialogDestination> by Delegates.notNull()
    override val pendingNavigationEvent: Flow<NavigationEvent> = flow {  }

    override fun navigateToLogin(replace: Boolean) = Unit
    override fun navigateToMain(replace: Boolean) = Unit
    override fun navigateToLoading(replace: Boolean) = Unit
    override fun navigateToDestination(replace: Boolean, destination: Destination) = Unit
    override fun replaceUpTo(
        destination: Destination,
        inclusive: Boolean,
        matchLast: Boolean,
        predicate: (Destination) -> Boolean
    ) = Unit

    override fun navigateBack() = Unit
    override fun closeOverlayNavigations() = Unit

    override fun popUpTo(
        inclusive: Boolean,
        matchLast: Boolean,
        predicate: (Destination) -> Boolean
    ) = Unit

    override fun removeDestinationFromLast(predicate: (Screen) -> Boolean) = Unit

    override fun showSheet(destination: SheetDestination) = Unit
    override fun showDialog(destination: DialogDestination, replace: Boolean) = Unit
    override fun setPendingNavigationEvent(event: NavigationEvent) = Unit
}

val LocalDestinationControllerProvider = staticCompositionLocalOf<DestinationController> {
    error("No default startDestination provided")
}

val LocalDestinationController
    @Composable get() = LocalDestinationControllerProvider.current
