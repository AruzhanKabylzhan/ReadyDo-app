package kz.rdd.core.ui.widgets.animated

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue

@Composable
fun BottomUpReveal(
    initialTranslation: Dp = 100.dp,
    animationSpec: AnimationSpec<Float> = tween(
        durationMillis = 200,
    ),
    content: @Composable () -> Unit
) {
    val animatable = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animatable.animateTo(
            1f,
            animationSpec = animationSpec
        )
    }

    val initialTranslationPx = with(LocalDensity.current) {
        initialTranslation.toPx()
    }
    Box(
        modifier = Modifier.graphicsLayer {
            alpha = animatable.value
            val scale = (animatable.value + 1.5f) / 2.5f
            scaleX = scale
            scaleY = scale
            translationY = initialTranslationPx * (1 - animatable.value)
        }
    ) {
        content()
    }

}