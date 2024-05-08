package kz.rdd.core.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.ext.advanceShadow
import kz.rdd.core.ui.ext.safeNavigationPadding
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun SafeBottomNavigation(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    content: @Composable RowScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .advanceShadow(
                blurRadius = 24.dp,
                borderRadius = 0.dp,
                offsetY = 2.dp,
                color = LocalAppTheme.colors.shadow,
            )
            .background(backgroundColor)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(76.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .safeNavigationPadding()
        )
    }
}