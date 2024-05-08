package kz.rdd.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.AnimatedNavHost
import dev.olshevski.navigation.reimagined.NavAction
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.moveToTop
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.popUpTo
import dev.olshevski.navigation.reimagined.rememberNavController
import kz.rdd.core.ui.LocalDestinationController
import kz.rdd.core.ui.base.AcceptResult
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.ext.clickableWithIndication
import kz.rdd.core.ui.ext.collectInLaunchedEffectWithLifecycle
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.SafeBottomNavigation
import kz.rdd.forefront_nav.ForefrontDestination
import kz.rdd.forefront_nav.ForefrontDestinationsDelegate
import org.koin.androidx.compose.getViewModel

@Composable
fun MainNavigationScreen(
    forefrontDestinationsDelegate: ForefrontDestinationsDelegate,
) {
    val viewModel = getViewModel<MainNavigationViewModel>()

    val destinationController = LocalDestinationController

    val destinations = remember {
        forefrontDestinationsDelegate.getDestinations()
    }
    val entryPointDestination = remember(destinations) {
        destinations.find { it.isEntryPoint } ?: destinations.first()
    }
    val navController = rememberNavController(
        startDestination = entryPointDestination
    )

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event)

    destinationController.pendingNavigationEvent.collectInLaunchedEffectWithLifecycle {
        viewModel.navigate(it)
    }

    BottomNavigationBackHandler(navController)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {

            AnimatedNavHost(
                controller = navController,
            ) { destination ->
                destination.Content(destinationController)
            }
        }

        SafeBottomNavigation {
            destinations.forEach { destination ->
                val lastDestination = navController.backstack.entries.last().destination
                val isSelected = destination == lastDestination
                val icon = destination.icon
                Box(
                    modifier = Modifier
                        .requiredSize(56.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(LocalAppTheme.colors.run {
                            if (isSelected) bgButtonSecondary else transparent
                        })
                        .clickableWithIndication(
                            rippleColor = LocalAppTheme.colors.bgButton,
                            bounded = false
                        ) {
                            // keep only one instance of a destination in the backstack
                            if (!navController.moveToTop { it == destination }) {
                                // if there is no existing instance, add it
                                navController.navigate(destination)
                            }
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = icon),
                        contentDescription = null,
                        tint = LocalAppTheme.colors.main
                    )
                }
            }
        }
    }

}

@Composable
private fun BottomNavigationBackHandler(
    navController: NavController<ForefrontDestination>
) {
    val controller = LocalDestinationController
    val isDialogsEmpty = controller.dialogNavController.backstack.entries.isEmpty()
    val isSheetsEmpty = controller.sheetNavController.backstack.entries.isEmpty()

    BackHandler(enabled = navController.backstack.entries.size > 1 && isDialogsEmpty && isSheetsEmpty) {
        val lastEntry = navController.backstack.entries.last()
        if (lastEntry.destination.isEntryPoint) {
            // The start destination should always be the last to pop. We move it to the start
            // to preserve its saved state and view models.
            navController.moveLastEntryToStart()
        } else {
            navController.popUpTo(inclusive = false) {
                it.isEntryPoint
            }
        }
    }
}

private fun NavController<ForefrontDestination>.moveLastEntryToStart() {
    setNewBackstack(
        entries = backstack.entries.toMutableList().also {
            val entry = it.removeLast()
            it.add(0, entry)
        },
        action = NavAction.Pop
    )
}