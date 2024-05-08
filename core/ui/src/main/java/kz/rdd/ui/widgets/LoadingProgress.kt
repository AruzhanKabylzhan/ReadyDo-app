package kz.rdd.core.ui.widgets

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.ext.advanceShadow
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun LoadingProgress(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
) {
    Crossfade(
        targetState = isVisible,
        modifier = modifier,
        label = "LoadingProgressCrossfade"
    ) {
        if (it) {
            Card(
                modifier = Modifier.advanceShadow(
                    color = LocalAppTheme.colors.main.copy(alpha = 0.1f),
                    blurRadius = 24.dp,
                ),
                backgroundColor = LocalAppTheme.colors.white,
                elevation = 0.dp,
                shape = RoundedCornerShape(16.dp),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(18.dp)
                        .size(36.dp),
                    color = LocalAppTheme.colors.main,
                    strokeWidth = 3.dp,
                    strokeCap = StrokeCap.Round,
                )
            }
        }
    }

}