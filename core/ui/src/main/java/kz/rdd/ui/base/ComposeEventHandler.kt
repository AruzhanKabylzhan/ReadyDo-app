@file:Suppress("UNCHECKED_CAST")

package kz.rdd.core.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.Flow
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.LocalDestinationController
import kz.rdd.core.ui.LocalDestinationControllerProvider
import kz.rdd.core.ui.base.effect.baseEffectHandler
import kz.rdd.core.ui.base.navigation.NavigationEvent
import kz.rdd.core.ui.base.navigation.navigationHandler
import kz.rdd.core.ui.base.viewmodel.UiEffect
import kz.rdd.core.ui.base.viewmodel.UiEvent
import kz.rdd.core.ui.ext.collectInLaunchedEffectWithLifecycle

@Composable
fun ComposeEventHandler(
    event: Flow<UiEvent>,
    controller: DestinationController = LocalDestinationController,
    onNavigationEvent: (NavigationEvent.() -> Unit)? = null,
    handleEvent: (suspend UiEvent.() -> Unit)? = null,
) {
    val context = LocalContext.current
    event.collectInLaunchedEffectWithLifecycle { consumingEvent ->
        when (consumingEvent) {
            is NavigationEvent -> navigationHandler(
                event = consumingEvent,
                controller = controller,
                context = context,
                handler = onNavigationEvent
            )
            else -> handleEvent?.invoke(consumingEvent)
        }
    }
}

@Composable
fun ComposeEffectHandler(
    effect: Flow<UiEffect>,
    handleEffect: (UiEffect.() -> Unit)? = null,
) {
    val context = LocalContext.current
    effect.collectInLaunchedEffectWithLifecycle {
        baseEffectHandler(
            effect = it,
            handle = handleEffect,
            context = context
        )
    }
}