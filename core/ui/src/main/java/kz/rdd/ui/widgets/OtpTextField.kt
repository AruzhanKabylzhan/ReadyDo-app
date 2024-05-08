@file:OptIn(ExperimentalComposeUiApi::class)

package kz.rdd.core.ui.widgets

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    hasError: Boolean,
    otpCount: Int = 4,
    onOtpTextChange: (String, end: Boolean) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }

        focusRequester.requestFocus()
    }

    BasicTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.hasFocus || it.isFocused) {
                    keyboardController?.show()
                }
            },
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(13.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText,
                        hasError = hasError,
                    )
                }
            }
        }
    )
}

@Composable
private fun RowScope.CharView(
    index: Int,
    text: String,
    hasError: Boolean,
) {
    val isFocused = remember(index, text) {
        text.length == index
    }
    val bgAnim by animateColorAsState(
        LocalAppTheme.colors.run {
            when {
                hasError -> error
                isFocused -> primaryText
                else -> stroke
            }
        }
    )
    val char = when {
        index == text.length -> "•"
        index > text.length -> "•"
        else -> text[index].toString()
    }
    Box(
        modifier = Modifier
            .weight(1f)
            .widthIn(max = 100.dp)
            .height(48.dp)
            .align(Alignment.CenterVertically)
            .border(
                1.dp,
                bgAnim,
                RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier,
            text = char,
            style = LocalAppTheme.typography.l24.copy(
                fontWeight = FontWeight.Normal,
            ),
            color = LocalAppTheme.colors.primaryText,
            textAlign = TextAlign.Center
        )
    }
}