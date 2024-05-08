package kz.rdd.core.ui.utils

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

data class DottedShape(
    val step: Dp,
    val radius: CornerRadius = CornerRadius(8.dp.value, 8.dp.value),
    val isHorizontal: Boolean = true,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(Path().apply {
        val width = if (isHorizontal) size.width else size.height
        val height = if (isHorizontal) size.height else size.width
        val stepPx = with(density) { step.toPx() }
        val stepsCount = (width / stepPx).roundToInt()
        val actualStep = width / stepsCount
        val dotSize = Size(
            width = if (isHorizontal) actualStep / 2 else height,
            height = if (isHorizontal) height else actualStep / 2
        )
        for (i in 0 until stepsCount) {
            val x = if (isHorizontal) i * actualStep else 0f
            val y = if (isHorizontal) 0f else i * actualStep
            val rect = Rect(
                offset = Offset(x = x, y = y),
                size = dotSize
            )
            addRoundRect(RoundRect(rect, radius))
        }
        close()
    })
}