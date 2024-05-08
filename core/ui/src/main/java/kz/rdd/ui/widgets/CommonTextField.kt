package kz.rdd.core.ui.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.theme.LocalAppTheme

@Composable
fun CommonTextField(
    value: String,
    onUpdate: (String) -> Unit,
    placeholderText: String,
    hasError: Boolean = false,
    errorMessage: String? = null,
    strokeColor: Color = LocalAppTheme.colors.stroke,
    isEnabled: Boolean = true,
    maxLines: Int = 1,
    maxLinesPlaceholder: Int = 1,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onClick: (() -> Unit)? = null,
    textStyle: TextStyle = LocalAppTheme.typography.l15,
    placeholderTextStyle: TextStyle = LocalAppTheme.typography.l15,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    Column {
        val bgColor by animateColorAsState(targetValue = LocalAppTheme.colors.run {
            if (isEnabled) white else gray6
        }, label = "")
        val interactionSource = remember { MutableInteractionSource() }
        if (onClick != null) {
            LaunchedEffect(interactionSource) {
                interactionSource.interactions.collect {
                    if (it is PressInteraction.Release) {
                        onClick.invoke()
                    }
                }
            }
        }
        BasicTextField(
            value = value,
            onValueChange = onUpdate,
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp),
            interactionSource = interactionSource,
            singleLine = maxLines == 1,
            enabled = isEnabled,
            maxLines = maxLines,
            readOnly = readOnly,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            textStyle = textStyle.copy(
                color = LocalAppTheme.colors.run {
                    when {
                        isEnabled -> primaryText
                        else -> accentText
                    }
                }
            ),
            cursorBrush = Brush.verticalGradient(
                if (hasError) {
                    listOf(
                        LocalAppTheme.colors.error,
                        LocalAppTheme.colors.error
                    )
                } else {
                    listOf(
                        LocalAppTheme.colors.primaryText,
                        LocalAppTheme.colors.primaryText
                    )
                }
            )
        ) { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = bgColor)
                    .border(
                        width = 1.dp,
                        color = LocalAppTheme.colors.run {
                            when {
                                hasError -> error
                                isEnabled -> primaryText
                                else -> bgGray
                            }
                        },
                        shape = RoundedCornerShape(12.dp)
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    leadingContent?.invoke()
                    Box(
                        modifier = Modifier
                            .weight(1f),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = if (value.isEmpty()) placeholderText else "",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 16.dp)
                                    .align(Alignment.CenterVertically),
                                style = placeholderTextStyle,
                                color = LocalAppTheme.colors.disabledText,
                                textAlign = TextAlign.Start,
                                maxLines = maxLinesPlaceholder,
                                overflow = TextOverflow.Ellipsis
                            )
                            trailingIcon?.invoke()
                        }
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                        ) {
                            innerTextField()
                        }
                    }
                }
            }
        }

        // Error text
        AnimatedVisibility(visible = !errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage.orEmpty(),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 8.dp),
                style = LocalAppTheme.typography.l12,
                color = LocalAppTheme.colors.error,
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Composable
fun CommonPasswordTextField(
    value: String,
    modifier: Modifier = Modifier,
    onUpdate: (String) -> Unit,
    hasError: Boolean = false,
    errorMessage: String? = null,
    isEnabled: Boolean = true,
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = LocalAppTheme.typography.l15,
    placeholderTextStyle: TextStyle = LocalAppTheme.typography.l15,
    placeholderText: String = stringResource(id = R.string.common_password)
) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    CommonTextField(
        value = value,
        onUpdate = onUpdate,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(
                    painter = painterResource(id = if (passwordVisibility) R.drawable.ic_password_hide else R.drawable.ic_password_toggle_24),
                    contentDescription = "",
                    tint = LocalAppTheme.colors.accentText,
                )
            }
        },
        placeholderText = placeholderText,
        hasError = hasError,
        errorMessage = errorMessage,
        isEnabled = isEnabled,
        readOnly = readOnly,
        onClick = onClick,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        placeholderTextStyle = placeholderTextStyle,
        modifier = modifier,
    )
}