package kz.rdd.core.ui.utils.annotated

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun HighlightedAnnotatedString(
    text: String,
    query: String = "",
    textColor: Color,
    backgroundColor: Color,
): AnnotatedString {
    val span = SpanStyle(
        color = textColor,
        background = backgroundColor
    )

    return buildAnnotatedString {
        var start = 0

        while (text.indexOf(query, start, ignoreCase = true) != -1 && query.isNotBlank()) {
            val highlightFirstIndex = text.indexOf(query, start, true)
            val highlightEndIndex = highlightFirstIndex + query.length

            append(text.substring(start, highlightFirstIndex))
            withStyle(style = span) {
                append(text.substring(highlightFirstIndex, highlightEndIndex))
            }

            start = highlightEndIndex
        }

        append(text.substring(start, text.length))
    }
}