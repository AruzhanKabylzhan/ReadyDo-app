package kz.rdd.core.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
fun UserScore(
    score: Int,
    modifier: Modifier = Modifier,
    verticalPadding: Dp = 2.dp,
    horizontalPadding: Dp = 8.dp,
    brush: Brush? = null,
) {
    val scoreColor = score.getScoreColor()
    val actualBrush = remember(brush) {
        brush ?: Brush.verticalGradient(
            listOf(
                scoreColor,
                scoreColor,
            )
        )
    }
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(actualBrush)
            .padding(horizontal = horizontalPadding, vertical = verticalPadding),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_score_16),
            contentDescription = null,
            tint = LocalAppTheme.colors.white
        )
        Text(
            text = score.toString(),
            style = LocalAppTheme.typography.l14B,
            color = LocalAppTheme.colors.white
        )
    }
}

@Composable
fun Int.getScoreColor(): Color {
    val score = this
    return LocalAppTheme.colors.run {
        when {
            score >= 95 -> highScore
            score in 81..94 -> mediumScore
            score in 70..80 -> lowScore
            else -> lowestScore
        }
    }
}
