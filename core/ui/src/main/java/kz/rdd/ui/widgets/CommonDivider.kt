package kz.rdd.core.ui.widgets

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun CommonDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = LocalAppTheme.colors.stroke
) {
    Divider(
        modifier = modifier,
        thickness = thickness,
        color = color
    )
}
