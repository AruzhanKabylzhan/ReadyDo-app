package kz.rdd.core.ui.utils.motion

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import kz.rdd.core.ui.utils.motion.animation.materialSharedAxisX
import kz.rdd.core.ui.utils.motion.animation.materialSharedAxisY
import kz.rdd.core.ui.utils.motion.animation.materialSharedAxisZ
import kz.rdd.core.ui.utils.motion.animation.rememberSlideDistance

/**
 * [MaterialSharedAxisX] allows to switch between two layouts with a shared X-axis animation.
 *
 * @see com.google.android.material.transition.MaterialSharedAxis
 *
 * @param targetState is a key representing your target layout state. Every time you change a key
 * the animation will be triggered. The [content] called with the old key will be faded out while
 * the [content] called with the new key will be faded in.
 * @param forward whether the direction of the animation is forward.
 * @param modifier Modifier to be applied to the animation container.
 * @param slideDistance slide distance of the animation.
 */
@Composable
fun <T> MaterialSharedAxisX(
    targetState: T,
    forward: Boolean,
    modifier: Modifier = Modifier,
    slideDistance: Dp = MotionConstants.DefaultSlideDistance,
    content: @Composable AnimatedVisibilityScope.(T) -> Unit,
) {
    val distance = rememberSlideDistance(slideDistance)
    MaterialMotion(
        targetState = targetState,
        transitionSpec = {
            materialSharedAxisX(
                forward = forward,
                slideDistance = distance,
            )
        },
        modifier = modifier,
        pop = forward.not(),
        content = content
    )
}

/**
 * [MaterialSharedAxisY] allows to switch between two layouts with a shared Y-axis animation.
 *
 * @see com.google.android.material.transition.MaterialSharedAxis
 *
 * @param targetState is a key representing your target layout state. Every time you change a key
 * the animation will be triggered. The [content] called with the old key will be faded out while
 * the [content] called with the new key will be faded in.
 * @param forward whether the direction of the animation is forward.
 * @param modifier Modifier to be applied to the animation container.
 * @param slideDistance slide distance of the animation.
 */
@Composable
public fun <T> MaterialSharedAxisY(
    targetState: T,
    forward: Boolean,
    modifier: Modifier = Modifier,
    slideDistance: Dp = MotionConstants.DefaultSlideDistance,
    content: @Composable AnimatedVisibilityScope.(T) -> Unit,
) {
    val distance = rememberSlideDistance(slideDistance)
    MaterialMotion(
        targetState = targetState,
        transitionSpec = {
            materialSharedAxisY(
                forward = forward,
                slideDistance = distance,
            )
        },
        modifier = modifier,
        pop = forward.not(),
        content = content
    )
}

/**
 * [MaterialSharedAxisZ] allows to switch between two layouts with a shared Z-axis animation.
 *
 * @see com.google.android.material.transition.MaterialSharedAxis
 *
 * @param targetState is a key representing your target layout state. Every time you change a key
 * the animation will be triggered. The [content] called with the old key will be faded out while
 * the [content] called with the new key will be faded in.
 * @param forward whether the direction of the animation is forward.
 * @param modifier Modifier to be applied to the animation container.
 */
@Composable
fun <T> MaterialSharedAxisZ(
    targetState: T,
    forward: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.(T) -> Unit,
) {
    MaterialMotion(
        targetState = targetState,
        transitionSpec = {
            materialSharedAxisZ(
                forward = forward,
            )
        },
        modifier = modifier,
        pop = forward.not(),
        content = content
    )
}