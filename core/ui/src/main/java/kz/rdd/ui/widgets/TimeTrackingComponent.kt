package kz.rdd.core.ui.widgets

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun TimeTrackingComponent(
    text: String,
    time: String,
    isChecked: Boolean = false,
    withAlpha: Boolean = false,
    @DrawableRes alternateStartIcon: Int? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    textEndContent: (@Composable RowScope.() -> Unit)? = null,
) {

    val bg by animateColorAsState(
        LocalAppTheme.colors.run {
            if (isChecked) checkGreen else white
        },
        label = "",
    )

    val hasSecondLine = time.isNotEmpty() || textEndContent != null
    val circlePaddingTop = if (hasSecondLine) 14.dp else 0.dp
    val circleAlignment = if (hasSecondLine) Alignment.Top else Alignment.CenterVertically

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .alpha(
                if (withAlpha) 0.4f else 1f
            )
            .padding(vertical = 4.dp)
            .padding(start = 13.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(top = circlePaddingTop)
                .size(30.dp)
                .align(circleAlignment),
            contentAlignment = Alignment.Center,
        ) {
            if (alternateStartIcon == null) {
                Row(
                    modifier = Modifier
                        .size(24.dp)
                        .border(
                            if (isChecked) 0.dp else 1.dp,
                            LocalAppTheme.colors.stroke,
                            CircleShape
                        )
                        .clip(CircleShape)
                        .background(bg),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AnimatedVisibility(visible = isChecked) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_check_16),
                            contentDescription = null,
                            tint = Color.Unspecified,
                        )
                    }
                }
            } else {
                Icon(
                    imageVector = ImageVector.vectorResource(alternateStartIcon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                )
            }
        }

        Spacer(modifier = Modifier.width(7.dp))

        TimeTrackingText(
            timeTrackTitle = text,
            time = time,
            modifier = Modifier.weight(1f),
            statusContent = textEndContent,
        )

        trailingContent?.let {
            Spacer(modifier = Modifier.width(8.dp))
            it()
        }
    }
}

@Composable
fun TimeTrackingButton(
    text: String,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(LocalAppTheme.colors.white)
    ) {
        CommonButton(
            text = text,
            isEnabled = isEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 16.dp),
            onClick = onClick
        )
    }
}

@Composable
fun TimeTrackingText(
    timeTrackTitle: String,
    time: String,
    modifier: Modifier = Modifier,
    statusContent: (@Composable RowScope.() -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = timeTrackTitle,
            style = LocalAppTheme.typography.l14B,
            color = LocalAppTheme.colors.primaryText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            if (time.isNotEmpty()) {
                Text(
                    text = time,
                    style = LocalAppTheme.typography.l14,
                    color = LocalAppTheme.colors.accentText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            statusContent?.invoke(this)
        }
    }
}