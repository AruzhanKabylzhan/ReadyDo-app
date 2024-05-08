package kz.rdd.core.ui.utils.motion

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import kz.rdd.core.ui.utils.motion.animation.materialFadeIn
import kz.rdd.core.ui.utils.motion.animation.materialFadeOut

/**
 * [MaterialFade] animates the appearance and disappearance of its content, as [visible] value changes.
 *
 * @see com.google.android.material.transition.MaterialFade
 *
 * @param visible defines whether the [content] should be visible
 * @param modifier modifier for the [Layout] created to contain the [content]
 * @param enterDurationMillis enter duration
 * @param exitDurationMillis exit duration
 */
@Composable
fun MaterialFade(
    visible: Boolean,
    modifier: Modifier = Modifier,
    enterDurationMillis: Int = MotionConstants.DefaultFadeInDuration,
    exitDurationMillis: Int = MotionConstants.DefaultFadeOutDuration,
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = materialFadeIn(enterDurationMillis),
        exit = materialFadeOut(exitDurationMillis)
    ) {
        content()
    }
}