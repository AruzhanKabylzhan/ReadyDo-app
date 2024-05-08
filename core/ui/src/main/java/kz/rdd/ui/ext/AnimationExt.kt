package kz.rdd.core.ui.ext

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith

fun AnimatedContentTransitionScope<*>.slideVerticallyStateAnimation(): ContentTransform {
    val duration = 200
    val enterFade = fadeIn(animationSpec = tween(duration, easing = EaseIn))
    val exitFade = fadeOut(animationSpec = tween(duration, easing = EaseOut))

    val enterSlide = slideInVertically(
        animationSpec = tween(duration),
        initialOffsetY = { it },
    )
    val exitSlide = slideOutVertically(animationSpec = tween(duration))

    return (enterFade + enterSlide).togetherWith(exitFade + exitSlide).using(
        SizeTransform(
            clip = false,
            sizeAnimationSpec = { _, _ ->
                spring(
                    Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessMediumLow,
                )
            }
        )
    )
}

fun AnimatedContentTransitionScope<*>.slideHorizontallyStateAnimation(): ContentTransform {
    val duration = 200
    val enterFade = fadeIn(animationSpec = tween(duration, easing = EaseIn))
    val exitFade = fadeOut(animationSpec = tween(duration, easing = EaseOut))

    val enterSlide = slideInHorizontally(
        animationSpec = tween(duration),
        initialOffsetX = { it },
    )
    val exitSlide = slideOutHorizontally(animationSpec = tween(duration))

    return (enterFade + enterSlide).togetherWith(exitFade + exitSlide).using(
        SizeTransform(
            clip = false,
            sizeAnimationSpec = { _, _ ->
                spring(
                    Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessMediumLow,
                )
            }
        )
    )
}

fun AnimatedContentTransitionScope<*>.sizeOutStateAnimation(): ContentTransform {
    val duration = 200
    val enterFade = fadeIn(animationSpec = tween(duration, easing = EaseIn))
    val exitFade = fadeOut(animationSpec = tween(duration, easing = EaseOut))

    val enterSlide = scaleIn(
        animationSpec = tween(duration),
    )

    val exitSlide = scaleOut(
        animationSpec = tween(duration),
    )

    return (enterFade + enterSlide).togetherWith(exitFade + exitSlide).using(
        SizeTransform(
            clip = false,
            sizeAnimationSpec = { _, _ ->
                spring(
                    Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessMediumLow,
                )
            }
        )
    )
}