package kz.rdd.core.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun ScoreInfo(
    score: Int,
    scorePrevious: Int
) {
    val scoreInfo = score - scorePrevious
    val triangleIcon = ImageVector.vectorResource(
        if (scoreInfo < 0) R.drawable.ic_triangle_bottom
        else R.drawable.ic_triangle_up
    )

    val color = LocalAppTheme.colors.run {
        if (scoreInfo < 0) error else checkGreen
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = triangleIcon,
            contentDescription = "",
            tint = color
        )

        Row(
            modifier = Modifier.padding(end = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = scoreInfo.toString(),
                style = LocalAppTheme.typography.l16,
                color = LocalAppTheme.colors.primaryText
            )

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_score_16),
                contentDescription = "",
                tint = color,
            )
        }
    }

}