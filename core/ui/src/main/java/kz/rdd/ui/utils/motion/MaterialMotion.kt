package kz.rdd.core.ui.utils.motion

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * [MaterialMotion] allows to switch between two layouts with a material motion animation.
 *
 * @param targetState is a key representing your target layout state. Every time you change a key
 * the animation will be triggered. The [content] called with the old key will be faded out while
 * the [content] called with the new key will be faded in.
 * @param transitionSpec the [ContentTransform] to configure the enter/exit animation.
 * @param modifier Modifier to be applied to the animation container.
 * @param pop whether motion contents are rendered in reverse order.
 */
@Composable
fun <S> MaterialMotion(
    targetState: S,
    transitionSpec: AnimatedContentTransitionScope<S>.() -> ContentTransform,
    modifier: Modifier = Modifier,
    pop: Boolean = false,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable AnimatedVisibilityScope.(targetState: S) -> Unit,
) {
    val transition = updateTransition(targetState = targetState, label = "MaterialMotion")
    transition.MaterialMotion(
        transitionSpec,
        modifier,
        pop,
        contentAlignment,
        content = content
    )
}

/**
 * [MaterialMotion] allows to switch between two layouts with a material motion animation.
 *
 * @param transitionSpec the [ContentTransform] to configure the enter/exit animation.
 * @param modifier Modifier to be applied to the animation container.
 * @param pop whether motion contents are rendered in reverse order.
 */
@Composable
fun <S> Transition<S>.MaterialMotion(
    transitionSpec: AnimatedContentTransitionScope<S>.() -> ContentTransform,
    modifier: Modifier = Modifier,
    pop: Boolean = false,
    contentAlignment: Alignment = Alignment.TopStart,
    contentKey: (targetState: S) -> Any? = { it },
    content: @Composable AnimatedVisibilityScope.(targetState: S) -> Unit,
) {
    val forward: Boolean = pop.not()
    val contentZIndex = remember { mutableFloatStateOf(0f) }
    AnimatedContent(
        modifier = modifier,
        transitionSpec = {
            val spec = transitionSpec()
            (spec.targetContentEnter togetherWith spec.initialContentExit)
                .apply {
                    // Show forward contents over the backward contents.
                    if (forward) {
                        contentZIndex.floatValue += 0.0001f
                    } else {
                        contentZIndex.floatValue -= 0.0001f
                    }
                    targetContentZIndex = contentZIndex.floatValue
                }
        },
        contentAlignment = contentAlignment,
        contentKey = contentKey
    ) { currentState ->
        content(currentState)
    }
}