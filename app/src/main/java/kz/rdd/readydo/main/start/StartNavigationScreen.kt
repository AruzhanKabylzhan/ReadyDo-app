
package kz.rdd.main.start

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.olshevski.navigation.reimagined.AnimatedNavHost
import dev.olshevski.navigation.reimagined.DialogNavHost
import dev.olshevski.navigation.reimagined.NavAction
import dev.olshevski.navigation.reimagined.NavTransitionScope
import dev.olshevski.navigation.reimagined.NavTransitionSpec
import dev.olshevski.navigation.reimagined.material.BottomSheetNavHost
import dev.olshevski.navigation.reimagined.material.BottomSheetPropertiesSpec
import dev.olshevski.navigation.reimagined.pop
import kz.rdd.core.ui.LocalDestinationControllerProvider
import kz.rdd.core.ui.LocalMainContentBlurProvider
import kz.rdd.core.ui.MainContentBlur
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.base.SheetDestination
import kz.rdd.core.ui.base.navigation.NavigationAnimation
import kz.rdd.core.ui.ext.safeStatusBarPadding
import kz.rdd.core.ui.theme.LocalAppTheme
import org.koin.androidx.compose.getViewModel

private val defaultBottomSheetPropertiesSpec = BottomSheetPropertiesSpec<SheetDestination> {
    it.sheetSpec
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun StartNavigationScreen(
    viewModel: GlobalViewModel = getViewModel()
) {
    val mainContentBlur = remember {
        MainContentBlur()
    }
    CompositionLocalProvider(
        LocalDestinationControllerProvider provides viewModel,
        LocalMainContentBlurProvider provides mainContentBlur,
    ) {
        Box(
            modifier = Modifier
                .background(LocalAppTheme.colors.white)
                .blur(mainContentBlur.blurRadius)
        ) {
            AnimatedNavHost(
                controller = viewModel.navController,
                transitionSpec = TransitionSpec
            ) {
                it.Content(viewModel)
            }

            BottomSheetNavHost(
                controller = viewModel.sheetNavController,
                sheetPropertiesSpec = defaultBottomSheetPropertiesSpec,
                sheetLayoutModifier = Modifier.safeStatusBarPadding(),
                scrimColor = Color.Black.copy(alpha = 0.7f),
                onDismissRequest = viewModel::onDismissSheet,
            ) { destination ->
                destination.Content(controller = viewModel)
            }

            DialogNavHost(
                controller = viewModel.dialogNavController
            ) {
                Dialog(
                    onDismissRequest = viewModel::onDismissDialog,
                    properties = DialogProperties(usePlatformDefaultWidth = false)
                ) {
                    it.OpenAnimation(controller = viewModel)
                }
            }
        }
    }

    val isDialogsEmpty = viewModel.dialogNavController.backstack.entries.isEmpty()
    val isSheetsEmpty = viewModel.sheetNavController.backstack.entries.isEmpty()


    LaunchedEffect(isDialogsEmpty) {
        mainContentBlur.setBlurRadius(
            if (isDialogsEmpty) {
                0.dp
            } else {
                viewModel.dialogNavController.backstack.entries.lastOrNull()?.destination?.blurRadius ?: 3.dp
            }
        )
    }

    BackHandler(
        enabled = viewModel.navController.backstack.entries.size > 1 || !isDialogsEmpty || !isSheetsEmpty
    ) {
        val hasDialog = viewModel.dialogNavController.backstack.entries.isNotEmpty()
        val hasSheet = viewModel.sheetNavController.backstack.entries.isNotEmpty()
        val screensCanBeBacked = viewModel.navController.backstack.entries.size > 1
        when {
            hasDialog -> {
                if (viewModel.dialogNavController.backstack.entries.last().destination.onNavigateBack()) {
                    viewModel.onDismissDialog()
                }
            }

            hasSheet -> {
                if (viewModel.sheetNavController.backstack.entries.last().destination.onNavigateBack()) {
                    viewModel.onDismissSheet()
                }
            }

            screensCanBeBacked -> {
                if (viewModel.navController.backstack.entries.last().destination.onNavigateBack()) {
                    viewModel.navController.pop()
                }
            }
        }
    }
}

@ExperimentalAnimationApi
private val TransitionSpec = object : NavTransitionSpec<Destination?> {

    override fun NavTransitionScope.getContentTransform(
        action: NavAction,
        from: Destination?,
        to: Destination?
    ): ContentTransform =
        (if (action is NavAction.Pop) from?.closeTransition(to) else to?.openTransition(
            action,
            from
        )) ?: NavigationAnimation.crossfade()

    override fun NavTransitionScope.toEmptyBackstack(
        action: NavAction,
        from: Destination?
    ): ContentTransform = NavigationAnimation.crossfade()

    override fun NavTransitionScope.fromEmptyBackstack(
        action: NavAction,
        to: Destination?
    ): ContentTransform = NavigationAnimation.crossfade()
}
