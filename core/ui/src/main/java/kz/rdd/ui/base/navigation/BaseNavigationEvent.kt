package kz.rdd.core.ui.base.navigation

import android.content.Context
import android.content.Intent
import android.widget.Toast
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.base.DialogDestination
import kz.rdd.core.ui.base.Screen
import kz.rdd.core.ui.base.SheetDestination
import kz.rdd.core.ui.base.viewmodel.UiEvent
import timber.log.Timber

interface NavigationEvent : UiEvent

object NavigateBack : NavigationEvent

object CloseOverlayNavigations : NavigationEvent

class NavigateTo(
    val screen: Destination,
    val replace: Boolean = false,
    val singleTop: Boolean = false,
) : NavigationEvent

class ReplaceUpTo(
    val screen: Destination,
    val inclusive: Boolean = false,
    val matchLast: Boolean = true,
    val predicate: (Destination) -> Boolean
) : NavigationEvent

class PopUpTo(
    val inclusive: Boolean = false,
    val matchLast: Boolean = true,
    val predicate: (Destination) -> Boolean
) : NavigationEvent

class ShowSheet(
    val destination: SheetDestination,
    val singleTop: Boolean = false,
) : NavigationEvent

class ShowDialog(
    val destination: DialogDestination,
    val replace: Boolean = false,
) : NavigationEvent

class RemoveDestinationFromLast(
    val predicate: (Screen) -> Boolean,
) : NavigationEvent

class NavigateToMain(
    val replace: Boolean = true,
) : NavigationEvent

class LinkedNavigationEvent(
    val events: List<NavigationEvent>
) : NavigationEvent

class StartActivity(
    val intent: Intent
) : NavigationEvent

open class ContextAction(
    val block: (Context) -> Unit
) : NavigationEvent

fun navigationHandler(
    event: NavigationEvent,
    controller: DestinationController,
    context: Context,
    handler: (NavigationEvent.() -> Unit)? = null
) {
    when (event) {
        is NavigateBack -> controller.navigateBack()
        is CloseOverlayNavigations -> controller.closeOverlayNavigations()
        is NavigateTo -> navigateTo(event, controller)
        is ShowSheet -> showSheet(event, controller)
        is ShowDialog -> controller.showDialog(event.destination, event.replace)
        is PopUpTo -> controller.popUpTo(event.inclusive, event.matchLast, event.predicate)
        is ReplaceUpTo -> controller.replaceUpTo(
            destination = event.screen,
            inclusive = event.inclusive,
            matchLast = event.matchLast,
            predicate = event.predicate
        )
        is LinkedNavigationEvent -> event.events.forEach {
            navigationHandler(
                event = it,
                controller = controller,
                context = context,
                handler = handler
            )
        }
        is RemoveDestinationFromLast -> controller.removeDestinationFromLast(event.predicate)
        is NavigateToMain -> controller.navigateToMain(event.replace)
        is StartActivity -> context.tryStartActivity(event.intent)
        is ContextAction -> event.block(context)
        else -> handler?.invoke(event)
    }
}

private fun navigateTo(
    event: NavigateTo,
    controller: DestinationController,
) {
    if (event.singleTop) {
        controller.navController.backstack.entries.lastOrNull()?.destination?.let {
            if (it::class == event.screen::class) return
        }
    }
    controller.navigateToDestination(replace = event.replace, event.screen)
}

private fun showSheet(
    event: ShowSheet,
    controller: DestinationController,
) {
    if (event.singleTop) {
        controller.navController.backstack.entries.lastOrNull()?.destination?.let {
            if (it::class == event.destination::class) return
        }
    }
    controller.showSheet(event.destination)
}

private fun Context.tryStartActivity(intent: Intent) {
    try {
        startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(
            this,
            getString(R.string.error_app_for_intent_not_found),
            Toast.LENGTH_SHORT
        ).show()
        Timber.e(e)
    }
}

