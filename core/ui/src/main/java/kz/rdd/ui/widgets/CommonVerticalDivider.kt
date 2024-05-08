package kz.rdd.core.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun CommonVerticalDivider(
    modifier: Modifier = Modifier,
    color: Color = LocalAppTheme.colors.stroke,
    thickness: Dp = 1.dp
) {
    Box(
        modifier
            .fillMaxHeight()
            .height(IntrinsicSize.Max)
            .width(thickness)
            .background(color = color)
    )
}