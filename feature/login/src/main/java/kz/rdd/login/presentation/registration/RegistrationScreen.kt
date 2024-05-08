package kz.rdd.login.presentation.registration

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.ext.safeNavigationPadding
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CommonButton
import kz.rdd.core.ui.widgets.CommonPasswordTextField
import kz.rdd.core.ui.widgets.CommonTextField
import org.koin.androidx.compose.getViewModel

@Parcelize
class RegistrationDestination : Destination {
    @Composable
    override fun Content(controller: DestinationController) {
        RegistrationScreen(controller)
    }

}

@Composable
fun RegistrationScreen(
    controller: DestinationController
) {

    val viewModel = getViewModel<RegistrationViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ComposeEventHandler(
        event = viewModel.event,
    )

    ComposeEffectHandler(
        effect = viewModel.effect,
    )

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            viewModel.setAvatar(uri ?: Uri.EMPTY)
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppTheme.colors.white)
            .imePadding()
            .safeNavigationPadding(),
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = "ReadyDo",
                style = LocalAppTheme.typography.h1.copy(
                    fontWeight = FontWeight.W800
                ),
                color = LocalAppTheme.colors.main,
            )
            Text(
                text = "with us",
                style = LocalAppTheme.typography.h2.copy(
                    fontWeight = FontWeight.W400
                ),
                color = LocalAppTheme.colors.main,
            )

            Spacer(modifier = Modifier.height(16.dp))

            CommonButton(
                text = "Add photo",
                modifier = Modifier.fillMaxWidth(),
                buttonColors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalAppTheme.colors.secondary,
                    disabledBackgroundColor = LocalAppTheme.colors.bgButtonSecondary,
                ),
                onClick = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            CommonTextField(
                value = state.firstName,
                placeholderText = "First Name",
                onUpdate = {
                    viewModel.onUpdateFirstName(it)
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            CommonTextField(
                value = state.lastName,
                placeholderText = "Last Name",
                onUpdate = {
                    viewModel.onUpdateLastName(it)
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            CommonTextField(
                value = state.username,
                placeholderText = "Username",
                onUpdate = {
                    viewModel.onUpdateUsername(it)
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            CommonTextField(
                value = state.phoneNumber,
                placeholderText = "Phone Number",
                onUpdate = {
                    viewModel.onUpdatePhoneNumber(it)
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            CommonTextField(
                value = state.email,
                placeholderText = "Email",
                onUpdate = {
                    viewModel.onUpdateEmail(it)
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            CommonTextField(
                value = state.address,
                placeholderText = "Address",
                onUpdate = {
                    viewModel.onUpdateAddressName(it)
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            CommonTextField(
                value = state.yourself,
                placeholderText = "About Yourself",
                onUpdate = {
                    viewModel.onUpdateYourselfName(it)
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            CommonPasswordTextField(
                value = state.password,
                onUpdate = {
                    viewModel.onUpdatePassword(it)
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            CommonPasswordTextField(
                value = state.repeatPassword,
                onUpdate = {
                    viewModel.onUpdateRepeatPassword(it)
                },
            )

            Spacer(modifier = Modifier.height(24.dp))

            CommonButton(
                text = stringResource(id = R.string.registration_save),
                modifier = Modifier.fillMaxWidth(),
                buttonColors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalAppTheme.colors.secondary,
                    disabledBackgroundColor = LocalAppTheme.colors.bgButtonSecondary,
                ),
                onClick = viewModel::onClickProceed,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .scaledClickable {
                        viewModel.navigateBack()
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_24),
                    contentDescription = "",
                    modifier = Modifier
                        .size(36.dp)
                )
            }

        }
    }
}