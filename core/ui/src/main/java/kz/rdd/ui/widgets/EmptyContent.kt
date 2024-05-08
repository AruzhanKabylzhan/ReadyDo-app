package kz.rdd.core.ui.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun EmptyContent(
    @DrawableRes icon: Int,
    title: String,
    description: String?,
    modifier: Modifier = Modifier,
    iconTint: Color = Color.Unspecified,
    action: @Composable (ColumnScope.() -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = null,
            tint = iconTint,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            style = LocalAppTheme.typography.l18,
            color = LocalAppTheme.colors.primaryText,
            textAlign = TextAlign.Center,
        )
        if (!description.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = LocalAppTheme.typography.l14M,
                color = LocalAppTheme.colors.accentText,
                textAlign = TextAlign.Center,
            )
        }
        action?.invoke(this)
    }
}
