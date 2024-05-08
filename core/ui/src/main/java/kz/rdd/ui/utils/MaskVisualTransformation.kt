package kz.rdd.core.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import kotlin.math.absoluteValue

class MaskVisualTransformation(
    private val prefix: String,
    private val mask: String,
    private val maskChar: Char = '#',
    private val hintChar: Char,
    private val hintColor: Color,
): VisualTransformation {
    private val specialSymbolsIndices = mask.indices.filter { mask[it] != maskChar }
    private val prefixOffset = prefix.length

    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        var maskIndex = 0
        text.forEach { char ->
            while (specialSymbolsIndices.contains(maskIndex)) {
                out += mask[maskIndex]
                maskIndex++
            }
            out += char
            maskIndex++
        }

        val builder = AnnotatedString.Builder()
        builder.append(prefix + out)

        while (maskIndex <= mask.lastIndex) {
            if (specialSymbolsIndices.contains(maskIndex)) builder.append(mask[maskIndex])
            else if (specialSymbolsIndices.isNotEmpty())
                builder.withStyle(style = SpanStyle(color = hintColor)) {
                    append(hintChar)
                }
            maskIndex++
        }

        return TransformedText(builder.toAnnotatedString(), prefixOffsetTranslator(text))
    }

    private fun prefixOffsetTranslator(text: AnnotatedString) = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val offsetValue = offset.absoluteValue
            if (offsetValue == 0) return prefixOffset

            var maskedCharsCount = 0
            val masked = mask.takeWhile {
                if (it == maskChar) maskedCharsCount++
                maskedCharsCount < offsetValue
            }

            return masked.length + 1 + prefixOffset
        }

        override fun transformedToOriginal(offset: Int): Int {
            val textSize = text.length
            if (offset > textSize - 1) return textSize
            val originalTextLength = mask.take(offset.absoluteValue).count { it == maskChar }

            return when {
                offset <= prefixOffset -> 0
                offset < mask.length -> originalTextLength - prefixOffset
                else -> originalTextLength
            }
        }
    }
}