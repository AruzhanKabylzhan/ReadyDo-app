package kz.rdd.core.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun LoadingBox(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        content()
        if (isLoading) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.5f),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 4.dp,
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = LocalAppTheme.colors.primaryText,
        trackColor = LocalAppTheme.colors.main,
    )
}