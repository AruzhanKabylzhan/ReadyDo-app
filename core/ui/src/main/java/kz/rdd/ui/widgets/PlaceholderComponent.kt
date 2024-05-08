package kz.rdd.core.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.theme.LocalAppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlaceholderComponent(
    title: String,
    description: String,
    icon: ImageVector,
    iconColor: Color = Color.Unspecified,
    pullRefreshState: PullRefreshState?
){
    val refreshModifier = if(pullRefreshState != null) Modifier.pullRefresh(pullRefreshState) else Modifier
    Column(
        modifier = refreshModifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            tint = iconColor
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title,
            style = LocalAppTheme.typography.l18B,
            color = LocalAppTheme.colors.primaryText
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = description,
            style = LocalAppTheme.typography.l14,
            color = LocalAppTheme.colors.accentText,
            textAlign = TextAlign.Center,
        )
    }
}