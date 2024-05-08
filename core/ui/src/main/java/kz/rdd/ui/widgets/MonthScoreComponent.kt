package kz.rdd.core.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.widgets.ScoreComponent
import kz.rdd.core.ui.widgets.getScoreColor
import kz.rdd.core.ui.R as uiR
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun MonthScoreComponent(
    monthText: String,
    monthScore: Int,
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 0.dp, vertical = 24.dp)
    ) {
        Text(
            text = monthText,
            style = LocalAppTheme.typography.l14.copy(
                fontWeight = FontWeight(600),
                fontFamily = FontFamily(Font(uiR.font.samsung_sans_regular))
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        ScoreComponent(score = monthScore, color = monthScore.getScoreColor())
    }
}