package kz.rdd.core.ui.widgets

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun AnimatedCounter(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle,
    color: Color = LocalAppTheme.colors.primaryText,
) {
    var oldCount by remember {
        mutableStateOf(text)
    }
    SideEffect {
        oldCount = text
    }
    Row(modifier = modifier) {
        val oldCountString = oldCount
        for (i in text.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = text[i]
            val char = if (oldChar == newChar) {
                oldCountString[i]
            } else {
                text[i]
            }
            AnimatedContent(
                targetState = char,
                transitionSpec = {
                    slideInVertically { it } togetherWith slideOutVertically { -it }
                }
            ) { i ->
                Text(
                    text = i.toString(),
                    style = style,
                    softWrap = false,
                    color = color,
                )
            }
        }
    }
}