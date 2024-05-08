package kz.rdd.core.ui.widgets.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.screen.CommonTextFieldDialogDestination
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.utils.VmRes
import kz.rdd.core.ui.utils.toStringCompose
import kz.rdd.core.ui.widgets.CommonButton
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DialogWithTextField(
    title: VmRes.Parcelable<CharSequence>,
    description: VmRes.Parcelable<CharSequence>,
    buttonText: VmRes.Parcelable<CharSequence>,
    behavior: CommonTextFieldDialogDestination.Behavior,
) {

    val viewModel = getViewModel<DialogWithTextFieldViewModel> {
        parametersOf(behavior)
    }

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event)

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(horizontal = 22.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = viewModel::navigateBack,
                modifier = Modifier
                    .background(LocalAppTheme.colors.white, shape = CircleShape)
                    .clip(shape = CircleShape),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_black_close),
                    contentDescription = "",
                    tint = LocalAppTheme.colors.black,
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = 16.dp
        ) {
            Column(
                Modifier.background(LocalAppTheme.colors.white)
            ) {
                Text(
                    text = title.toStringCompose(),
                    style = LocalAppTheme.typography.l18B,
                    color = LocalAppTheme.colors.primaryText,
                    modifier = Modifier.padding(top = 24.dp, start = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = description.toStringCompose(),
                    style = LocalAppTheme.typography.l15,
                    color = LocalAppTheme.colors.primaryText,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                OutlinedTextField(
                    value = state.value.field,
                    onValueChange = viewModel::onUpdateTextField,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = LocalAppTheme.colors.accentText,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    shape = RoundedCornerShape(16.dp),
                    minLines = 8,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = LocalAppTheme.colors.primaryText,
                        focusedBorderColor = LocalAppTheme.colors.accentText
                    )
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(LocalAppTheme.colors.white)
                ) {
                    CommonButton(
                        text = buttonText.toStringCompose(),
                        isLoading = state.value.loading,
                        isEnabled = state.value.isEnabled,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                        onClick = viewModel::onClickButton
                    )
                }
            }
        }
    }
}
