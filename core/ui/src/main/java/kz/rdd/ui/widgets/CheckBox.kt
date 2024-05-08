package kz.rdd.core.ui.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun CheckBox(
    isChecked: Boolean,
    isCircle: Boolean = true,
    isCheckedLate: Boolean = false,
    background: Color = run {
        val color by animateColorAsState(
            LocalAppTheme.colors.run {
                if (isCheckedLate) bgLate else if(isChecked) checkGreen else white
            },
            label = "",
        )
        color
    },
) {
    val shape = if (isCircle) CircleShape else RoundedCornerShape(8.dp)
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(shape)
            .border(
                if (isChecked) 0.dp else 1.dp,
                LocalAppTheme.colors.stroke,
                shape
            )
            .background(background),
        contentAlignment = Alignment.Center,
    ) {
        CheckedCircleContent(isChecked = isChecked)
    }
}

@Composable
private fun CheckedCircleContent(
    isChecked: Boolean
) {
    AnimatedVisibility(visible = isChecked) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_check_16),
            contentDescription = null,
            tint = Color.Unspecified,
        )
    }
}
