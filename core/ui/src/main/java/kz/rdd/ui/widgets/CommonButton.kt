package kz.rdd.core.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.theme.PreviewAppTheme

@Composable
fun CommonButton(
    text: String,
    modifier: Modifier = Modifier,
    modifierButton: Modifier = Modifier.fillMaxSize(),
    startIcon: Painter? = null,
    endIcon: Painter? = null,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    border: BorderStroke? = null,
    textColor: Color = LocalAppTheme.colors.white,
    disabledTextColor: Color = LocalAppTheme.colors.accentText,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = LocalAppTheme.colors.bgButton,
        disabledBackgroundColor = LocalAppTheme.colors.bgButtonSecondary,
    ),
    iconTint: Color = Color.Unspecified,
    minHeight: Dp = 48.dp,
    maxHeight: Dp = 48.dp,
    textStyle: TextStyle = LocalAppTheme.typography.l16.copy(
        fontWeight = FontWeight.W500,
    ),
    content: @Composable RowScope.() -> Unit = {
        startIcon?.also {
            Icon(
                painter = it,
                contentDescription = null,
                tint = iconTint,
            )
        }
        Text(
            text = text,
            color = if (isEnabled) textColor else disabledTextColor,
            style = textStyle,
            modifier = Modifier.padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
        )
        endIcon?.also {
            Icon(
                painter = it,
                contentDescription = null,
                tint = iconTint,
            )
        }
    },
    shape: Shape = RoundedCornerShape(8.dp),
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .heightIn(minHeight, maxHeight),
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(minHeight - 8.dp),
                color = LocalAppTheme.colors.bgButton,
                backgroundColor = LocalAppTheme.colors.bgButtonSecondary
            )
        } else {
            Button(
                onClick = onClick,
                modifier = modifierButton,
                shape = shape,
                elevation = null,
                enabled = isEnabled,
                colors = buttonColors,
                border = border,
                content = content,
            )
        }
    }
}

@Preview
@Composable
private fun CommonButtonPreview() {
    PreviewAppTheme {
        CommonButton(
            text = "Button",
            onClick = {}
        )
    }
}
