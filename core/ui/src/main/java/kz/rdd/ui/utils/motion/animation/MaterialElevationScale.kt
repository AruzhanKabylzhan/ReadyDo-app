package kz.rdd.core.ui.utils.motion.animation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import kz.rdd.core.ui.utils.motion.MotionConstants

/**
 * [materialElevationScaleIn] allows to switch a layout with elevation scale enter transition.
 *
 * @param initialAlpha the starting alpha of the enter transition.
 * @param initialScale the starting scale of the enter transition.
 * @param durationMillis the duration of the enter transition.
 */
fun materialElevationScaleIn(
    initialAlpha: Float = 0.85f,
    initialScale: Float = 0.85f,
    durationMillis: Int = MotionConstants.DefaultMotionDuration,
): EnterTransition = fadeIn(
    animationSpec = tween(
        durationMillis = durationMillis,
        easing = LinearEasing
    ),
    initialAlpha = initialAlpha
) + scaleIn(
    animationSpec = tween(
        durationMillis = durationMillis,
        easing = FastOutSlowInEasing
    ),
    initialScale = initialScale
)

/**
 * [materialElevationScaleOut] allows to switch a layout with elevation scale exit transition.
 *
 * @param targetAlpha the target alpha of the exit transition.
 * @param targetScale the target scale of the exit transition.
 * @param durationMillis the duration of the exit transition.
 */
fun materialElevationScaleOut(
    targetAlpha: Float = 0.85f,
    targetScale: Float = 0.85f,
    durationMillis: Int = MotionConstants.DefaultMotionDuration,
): ExitTransition = fadeOut(
    animationSpec = tween(
        durationMillis = durationMillis,
        easing = LinearEasing
    ),
    targetAlpha = targetAlpha
) + scaleOut(
    animationSpec = tween(
        durationMillis = durationMillis,
        easing = FastOutSlowInEasing
    ),
    targetScale = targetScale,
)