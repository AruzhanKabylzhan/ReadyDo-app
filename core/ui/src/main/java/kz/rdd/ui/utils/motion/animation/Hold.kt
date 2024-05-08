package kz.rdd.core.ui.utils.motion.animation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import kz.rdd.core.ui.utils.motion.MotionConstants

/**
 * [holdIn] allows to switch a layout with no enter transition.
 *
 * @param durationMillis the duration of the enter transition.
 */
fun holdIn(
    durationMillis: Int = MotionConstants.DefaultMotionDuration,
): EnterTransition = fadeIn(
    animationSpec = tween(
        durationMillis = durationMillis,
        easing = LinearEasing
    ),
    initialAlpha = 1f,
)

/**
 * [holdOut] allows to switch a layout with no exit transition.
 *
 * @param durationMillis the duration of the exit transition.
 */
fun holdOut(
    durationMillis: Int = MotionConstants.DefaultMotionDuration,
): ExitTransition = fadeOut(
    animationSpec = tween(
        durationMillis = durationMillis,
        easing = LinearEasing
    ),
    targetAlpha = 0.999f
)