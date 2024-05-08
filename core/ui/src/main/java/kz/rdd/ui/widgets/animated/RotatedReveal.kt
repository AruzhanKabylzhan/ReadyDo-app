package kz.rdd.core.ui.widgets.animated

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.absoluteValue

@Composable
fun RotatedReveal(
    content: @Composable () -> Unit
) {
    val animatableRotation = remember { Animatable(-90f) }

    LaunchedEffect(Unit) {
        animatableRotation.animateTo(
            0f,
            animationSpec = spring(
                dampingRatio = 0.6f,
                stiffness = Spring.StiffnessMediumLow,
            )
        )
    }

    Box(
        modifier = Modifier.graphicsLayer {
            rotationZ = animatableRotation.value
            val normalizedValue = 1 - animatableRotation.value.absoluteValue / 180f
            alpha = normalizedValue
            scaleX = normalizedValue
            scaleY = normalizedValue
        }
    ) {
        content()
    }

}