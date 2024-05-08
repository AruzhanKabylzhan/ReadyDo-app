package kz.rdd.core.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun CommonTextFieldWithTitle(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    maxLines: Int = 1,
    isEnabled: Boolean = true,
    hasError: Boolean = false,
    errorMessage: String? = null,
    placeholder: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onUpdate: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    CommonFieldWithTitle(
        modifier = modifier,
        title = title,
    ) {
        CommonTextField(
            value = value,
            onUpdate = onUpdate,
            maxLines = maxLines,
            hasError = hasError,
            errorMessage = errorMessage,
            placeholderText = placeholder,
            keyboardOptions = keyboardOptions,
            isEnabled = isEnabled,
            trailingIcon = trailingIcon
        )
    }
}

@Composable
fun CommonPasswordFieldWithTitle(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    isEnabled: Boolean = true,
    hasError: Boolean = false,
    errorMessage: String? = null,
    placeholder: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onUpdate: (String) -> Unit
) {
    CommonFieldWithTitle(
        modifier = modifier,
        title = title,
    ) {
        CommonPasswordTextField(
            value = value,
            onUpdate = onUpdate,
            hasError = hasError,
            errorMessage = errorMessage,
            placeholderText = placeholder,
            keyboardOptions = keyboardOptions,
            isEnabled = isEnabled,
        )
    }
}

@Composable
fun CommonFieldWithTitle(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title.uppercase(),
            style = LocalAppTheme.typography.l12,
            color = LocalAppTheme.colors.accentText,
        )
        content()
    }
}