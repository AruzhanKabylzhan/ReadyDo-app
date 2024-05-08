package kz.rdd.core.ui.ext

import android.graphics.BlurMaskFilter
import android.view.ViewTreeObserver
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitLongPressOrCancellation
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.utils.dragUntilConsumed
import kz.rdd.core.ui.utils.waitForUpRegionSafe

inline fun Modifier.addIf(
    condition: Boolean,
    crossinline factory: @Composable Modifier.() -> Modifier,
): Modifier = composed {
    if (condition) factory() else this
}

fun Modifier.clickableWithoutRipple(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier = composed {
    clickable(
        enabled = enabled,
        indication = null,
        onClickLabel = onClickLabel,
        role = role,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick,
    )
}

fun Modifier.clickableWithIndication(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    bounded: Boolean = true,
    debounceInterval: Long = 300,
    rippleColor: Color = Color.Unspecified,
    indication: @Composable () -> Indication = {
        rememberRipple(bounded = bounded, color = rippleColor)
    },
    onClick: () -> Unit,
): Modifier = composed {
    var lastClickTime by remember { mutableLongStateOf(0L) }
    clickable(
        enabled = enabled,
        indication = indication(),
        onClickLabel = onClickLabel,
        role = role,
        interactionSource = remember { MutableInteractionSource() },
        onClick = {
            val currentTime = System.currentTimeMillis()
            if ((currentTime - lastClickTime) < debounceInterval) return@clickable
            lastClickTime = currentTime
            onClick()
        },
    )
}


enum class ButtonState { Pressed, Idle }

fun Modifier.scaledClickable(
    scalePercent: Float = 0.95f,
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(
        if (buttonState == ButtonState.Pressed) scalePercent else 1f,
        label = ""
    )
    LaunchedEffect(buttonState) {
        if (buttonState == ButtonState.Pressed) {
            delay(500L)
            buttonState = ButtonState.Idle
        }
    }
    this
        .graphicsLayer {
            this.scaleY = scale
            this.scaleX = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            enabled = enabled,
            onClick = onClick,
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}

fun Modifier.alphaClickable(
    alphaPercent: Float = 0.7f,
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val alpha by animateFloatAsState(
        if (buttonState == ButtonState.Pressed) alphaPercent else 1f,
        label = ""
    )
    LaunchedEffect(buttonState) {
        if (buttonState == ButtonState.Pressed) {
            delay(500L)
            buttonState = ButtonState.Idle
        }
    }
    this
        .graphicsLayer {
            this.alpha = alpha
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            enabled = enabled,
            onClick = onClick,
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}

fun Modifier.longPress(
    onClick: () -> Unit,
    onLongPressStart: (PointerInputChange?) -> Unit,
    onLongPressEnd: (PointerInputChange) -> Unit,
    onDrag: (PointerInputChange) -> Unit = {},
    onDragEnd: (PointerInputChange) -> Unit = {},
) = pointerInput(Unit) {
    awaitEachGesture {
        val down = awaitFirstDown(requireUnconsumed = false)
        val change = awaitLongPressOrCancellation(down.id)
        val wasLongPress = (change == down)
        if (!wasLongPress) {
            onClick()
            return@awaitEachGesture
        }
        onLongPressStart(change)
        if (change != null) {
            var lastChange = change
            val isCompleted = dragUntilConsumed(change) {
                onDrag(it)
                lastChange = it
            }

            if (isCompleted) {
                onDragEnd(lastChange!!)

                return@awaitEachGesture
            }
        }
        onLongPressEnd(waitForUpRegionSafe())
    }
}

fun Modifier.clearFocusOnKeyboardDismiss(
    onKeyBoardClosed: () -> Unit = {},
): Modifier = composed {
    var isFocused by remember { mutableStateOf(false) }
    var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }

    if (isFocused) {
        val isKeyboardOpen by rememberKeyboardOpenState()

        val focusManager = LocalFocusManager.current
        LaunchedEffect(isKeyboardOpen) {
            if (isKeyboardOpen) {
                keyboardAppearedSinceLastFocused = true
            } else if (keyboardAppearedSinceLastFocused) {
                focusManager.clearFocus()
                onKeyBoardClosed()
            }
        }
    }
    onFocusEvent {
        if (isFocused != it.isFocused) {
            isFocused = it.isFocused
            if (isFocused) {
                keyboardAppearedSinceLastFocused = false
            }
        }
    }
}

@Composable
fun rememberKeyboardOpenState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}

@Composable
fun getSafeNavigationPadding(): PaddingValues {
    return WindowInsets.systemBars
        .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
        .asPaddingValues()
}

@Composable
fun getSafeStatusBarPadding(): PaddingValues {
    return WindowInsets.systemBars
        .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
        .asPaddingValues()
}

fun Modifier.safeNavigationPadding() = composed {
    padding(getSafeNavigationPadding())
}

fun Modifier.safeStatusBarPadding() = composed {
    padding(getSafeStatusBarPadding())
}

fun Modifier.backgroundShimmer(
    color: Color? = null,
    shape: Shape = RoundedCornerShape(8.dp)
): Modifier = composed {
    background(
        color = color ?: LocalAppTheme.colors.shimmer,
        shape = shape
    ).then(clip(shape))
}

fun Modifier.withShimmer(
    blendMode: BlendMode = BlendMode.DstAtop
): Modifier = composed {
    val shimmer = rememberShimmer(
        shimmerBounds = ShimmerBounds.View,
        theme = defaultShimmerTheme.copy(
            animationSpec = infiniteRepeatable(
                animation = tween(
                    800,
                    easing = LinearEasing,
                    delayMillis = 200,
                ),
                repeatMode = RepeatMode.Restart,
            ),
            blendMode = blendMode,
            shaderColors = listOf(
                LocalAppTheme.colors.shimmer.copy(alpha = 0.9f),
                LocalAppTheme.colors.white,
                LocalAppTheme.colors.shimmer.copy(alpha = 0.9f),
            ),
            rotation = 50f
        )
    )
    shimmer(shimmer)
}

fun Modifier.advanceShadow(
    color: Color = Color.Black,
    borderRadius: Dp = 16.dp,
    blurRadius: Dp = 16.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Float = 1f,
) = composed {
    val paint: Paint = remember { Paint() }
    val frameworkPaint = remember(paint) {
        paint.asFrameworkPaint()
    }
    val blurRadiusPx = with(LocalDensity.current) {
        blurRadius.toPx()
    }
    val maskFilter = remember {
        BlurMaskFilter(blurRadiusPx, BlurMaskFilter.Blur.NORMAL)
    }
    drawBehind {
        this.drawIntoCanvas {
            val spreadPixel = spread.dp.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter = maskFilter
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    }
}

fun Modifier.dashedBorder(
    width: Dp = 1.dp,
    radius: Dp = 0.dp,
    color: Color,
) = drawBehind {
    drawIntoCanvas {
        val paint = Paint()
            .apply {
                strokeWidth = width.toPx()
                this.color = color
                style = PaintingStyle.Stroke
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            }
        it.drawRoundRect(
            width.toPx(),
            width.toPx(),
            size.width - width.toPx(),
            size.height - width.toPx(),
            radius.toPx(),
            radius.toPx(),
            paint
        )
    }
}