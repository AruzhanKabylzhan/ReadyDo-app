package kz.rdd.core.ui.utils.motion

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kz.rdd.core.ui.utils.motion.animation.materialFadeThrough

/**
 * [MaterialFadeThrough] allows to switch between two layouts with a fade through animation.
 *
 * @see com.google.android.material.transition.MaterialFadeThrough
 *
 * @param targetState is a key representing your target layout state. Every time you change a key
 * the animation will be triggered. The [content] called with the old key will be faded out while
 * the [content] called with the new key will be faded in.
 * @param modifier Modifier to be applied to the animation container.
 */
@Composable
fun <T> MaterialFadeThrough(
    targetState: T,
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.(T) -> Unit,
) {
    MaterialMotion(
        targetState = targetState,
        transitionSpec = {
            materialFadeThrough()
        },
        modifier = modifier,
        content = content
    )
}