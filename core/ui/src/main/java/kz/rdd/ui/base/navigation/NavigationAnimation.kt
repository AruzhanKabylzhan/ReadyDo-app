package kz.rdd.core.ui.base.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import kz.rdd.core.ui.utils.motion.animation.materialFadeThrough
import kz.rdd.core.ui.utils.motion.animation.materialSharedAxisX
import kz.rdd.core.ui.utils.motion.animation.materialSharedAxisY
import kz.rdd.core.ui.utils.motion.animation.materialSharedAxisZ

@OptIn(ExperimentalAnimationApi::class)
object NavigationAnimation {
    fun slideVerticalFromTop() = materialSharedAxisY(
        forward = false,
        slideDistance = 300
    )
    fun slideVerticalFromBottom() = materialSharedAxisY(
        forward = true,
        slideDistance = 300
    )

    fun slideHorizontalFromEnd() = materialSharedAxisX(
        forward = true,
        slideDistance = 300
    )
    fun slideHorizontalFromStart() = materialSharedAxisX(
        forward = false,
        slideDistance = 300
    )

    fun scaleStart() = materialSharedAxisZ(
        forward = true,
    )

    fun scaleEnd() = materialSharedAxisZ(
        forward = false,
    )
    fun crossfade() = materialFadeThrough()
}
