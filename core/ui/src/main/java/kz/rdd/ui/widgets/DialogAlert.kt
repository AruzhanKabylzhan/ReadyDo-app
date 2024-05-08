package kz.rdd.core.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.ext.clickableWithIndication
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun DialogAlert(
    title: String,
    description: String? = null,
    horizontalPadding: Dp = 24.dp,
    actions: @Composable RowScope.() -> Unit,
) {
    Card(
        modifier = Modifier.padding(horizontal = horizontalPadding),
        backgroundColor = LocalAppTheme.colors.white,
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                style = LocalAppTheme.typography.h3,
                color = LocalAppTheme.colors.primaryText
            )
            if (!description.isNullOrEmpty()) Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp),
                text = description,
                textAlign = TextAlign.Center,
                style = LocalAppTheme.typography.l14,
                color = LocalAppTheme.colors.primaryText
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp),
                content = actions
            )
        }
    }
}

@Composable
fun DialogAlert(
    title: String,
    description: String? = null,
    action: String = "OK",
    cancel: String = stringResource(id = R.string.common_cancel),
    horizontalPadding: Dp = 24.dp,
    actionPositiveColor: Color = LocalAppTheme.colors.main,
    actionNegativeColor: Color = LocalAppTheme.colors.main,
    onActionPositive: () -> Unit = { },
    onActionNegative: () -> Unit = { }
) {
    DialogAlert(
        title = title,
        description = description,
        horizontalPadding = horizontalPadding,
        actions = {
            Text(
                text = action,
                modifier = Modifier
                    .weight(1f)
                    .clickableWithIndication {
                        onActionPositive()
                    }
                    .padding(vertical = 16.dp, horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = LocalAppTheme.typography.l15,
                color = actionPositiveColor
            )
            Text(
                text = cancel,
                modifier = Modifier
                    .weight(1f)
                    .clickableWithIndication {
                        onActionNegative()
                    }
                    .padding(vertical = 16.dp, horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = LocalAppTheme.typography.l15,
                color = actionNegativeColor
            )
        }
    )
}