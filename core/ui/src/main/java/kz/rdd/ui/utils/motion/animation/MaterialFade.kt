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
import kz.rdd.core.ui.utils.motion.MotionConstants

private const val DefaultFadeEndThresholdEnter = 0.3f

private val Int.ForFade: Int
    get() = (this * DefaultFadeEndThresholdEnter).toInt()

/**
 * [materialFadeIn] allows to switch a layout with a fade-in animation.
 */
fun materialFadeIn(
    durationMillis: Int = MotionConstants.DefaultFadeInDuration,
): EnterTransition = fadeIn(
    animationSpec = tween(
        durationMillis = durationMillis.ForFade,
        easing = LinearEasing
    )
) + scaleIn(
    animationSpec = tween(
        durationMillis = durationMillis,
        easing = FastOutSlowInEasing
    ),
    initialScale = 0.8f
)

/**
 * [materialFadeOut] allows to switch a layout with a fade-out animation.
 */
fun materialFadeOut(
    durationMillis: Int = MotionConstants.DefaultFadeOutDuration,
): ExitTransition = fadeOut(
    animationSpec = tween(
        durationMillis = durationMillis,
        easing = LinearEasing
    )
)