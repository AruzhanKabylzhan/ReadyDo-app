package kz.rdd.core.ui.utils.motion.animation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import kz.rdd.core.ui.utils.motion.MotionConstants

/**
 * [translateXIn] allows to switch a layout with a translate animation as x axis.
 */
fun translateXIn(
    durationMillis: Int = MotionConstants.DefaultMotionDuration,
    initialOffsetX: (fullHeight: Int) -> Int = { -it / 2 },
): EnterTransition = slideInHorizontally(
    animationSpec = tween(durationMillis),
    initialOffsetX = initialOffsetX
)

/**
 * [translateXOut] allows to switch a layout with a translate animation as x axis.
 */
fun translateXOut(
    durationMillis: Int = MotionConstants.DefaultMotionDuration,
    targetOffsetX: (fullHeight: Int) -> Int = { -it / 2 },
): ExitTransition = slideOutHorizontally(
    animationSpec = tween(durationMillis),
    targetOffsetX = targetOffsetX
)

/**
 * [translateYIn] allows to switch a layout with a translate animation as y axis.
 */
fun translateYIn(
    durationMillis: Int = MotionConstants.DefaultMotionDuration,
    initialOffsetY: (fullHeight: Int) -> Int = { -it / 2 },
): EnterTransition = slideInVertically(
    animationSpec = tween(durationMillis),
    initialOffsetY = initialOffsetY
)

/**
 * [translateYOut] allows to switch a layout with a translate animation as y axis.
 */
fun translateYOut(
    durationMillis: Int = MotionConstants.DefaultMotionDuration,
    targetOffsetY: (fullHeight: Int) -> Int = { -it / 2 },
): ExitTransition = slideOutVertically(
    animationSpec = tween(durationMillis),
    targetOffsetY = targetOffsetY
)