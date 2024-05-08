package kz.rdd.core.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class MainContentBlur {
    private val blurState = Animatable(0.dp, Dp.VectorConverter)

    val blurRadius get() = blurState.value

    suspend fun setBlurRadius(value: Dp) {
        if (blurRadius == value) return

        blurState.animateTo(
            targetValue = value,
        )
    }
}

val LocalMainContentBlurProvider = compositionLocalOf { MainContentBlur() }
