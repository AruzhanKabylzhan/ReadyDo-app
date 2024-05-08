package kz.rdd.core.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun BusinessmanTariffName(
    tariffName: String,
    modifier: Modifier = Modifier,
    tariffBrush: Brush,
    tariffTextColor: Color,
    verticalPadding: Dp = 4.dp,
    horizontalPadding: Dp = 12.dp,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(tariffBrush)
            .padding(horizontal = horizontalPadding, vertical = verticalPadding),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AnimatedCounter(
            text = tariffName,
            style = LocalAppTheme.typography.l14B,
            color = tariffTextColor,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right_16),
            contentDescription = "",
            tint = tariffTextColor
        )
    }
}