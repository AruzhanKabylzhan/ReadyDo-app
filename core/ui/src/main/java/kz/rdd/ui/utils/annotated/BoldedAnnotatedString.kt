package kz.rdd.core.ui.utils.annotated

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle

@Composable
fun BoldedAnnotatedString(
    text: String,
    boldText: String = "",
    boldTextStyle: TextStyle,
    boldTextColor: Color,
): AnnotatedString {
    val start = text.indexOf(boldText)
    val spanStyles = listOf(
        AnnotatedString.Range(
            item = SpanStyle(fontWeight = boldTextStyle.fontWeight, color = boldTextColor),
            start = start,
            end = start + boldText.length,
        )
    )

    return AnnotatedString(text = text, spanStyles = spanStyles)
}