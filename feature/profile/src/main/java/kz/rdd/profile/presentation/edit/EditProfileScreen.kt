package kz.rdd.profile.presentation.edit

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.ext.safeNavigationPadding
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.R
import kz.rdd.core.ui.widgets.AccountUserAvatarSettings
import kz.rdd.core.ui.widgets.CommonButton
import kz.rdd.core.ui.widgets.CommonTextFieldWithTitle
import org.koin.androidx.compose.getViewModel

@Parcelize
class EditProfileDestination : Destination {
    @Composable
    override fun Content(controller: DestinationController) {
        EditProfileScreen()
    }
}

@Composable
fun EditProfileScreen() {
    val viewModel = getViewModel<EditProfileViewModel>()
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
            .imePadding()
            .safeNavigationPadding(),
    ) {
        Box(
            modifier = Modifier
                .background(LocalAppTheme.colors.white)
                .fillMaxHeight(0.1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "ReadyDo",
                style = LocalAppTheme.typography.h1,
                color = LocalAppTheme.colors.main,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Edit Profile",
                style = LocalAppTheme.typography.h2,
                color = LocalAppTheme.colors.primaryText,
            )

            Spacer(modifier = Modifier.height(24.dp))

            AccountUserAvatarSettings(
                url = state.selectedAvatar ?: state.currentAvatar,
                onClickChangeUserAvatar = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            CommonTextFieldWithTitle(
                modifier = Modifier,
                title = "Phone number",
                value = state.phoneNumber,
                isEnabled = true,
                onUpdate = viewModel::onUpdatePhoneNumber
            )

            Spacer(modifier = Modifier.height(8.dp))

            CommonTextFieldWithTitle(
                modifier = Modifier,
                title = "First Name",
                value = state.firstName,
                isEnabled = true,
                onUpdate = viewModel::onUpdateFirstName
            )

            Spacer(modifier = Modifier.height(8.dp))

            CommonTextFieldWithTitle(
                modifier = Modifier,
                title = "Last Name",
                value = state.lastName,
                isEnabled = true,
                onUpdate = viewModel::onUpdateLastName
            )

            Spacer(modifier = Modifier.height(8.dp))

            CommonTextFieldWithTitle(
                modifier = Modifier,
                title = "Address",
                value = state.address,
                isEnabled = true,
                onUpdate = viewModel::onUpdateAddress
            )

            Spacer(modifier = Modifier.height(8.dp))

            CommonTextFieldWithTitle(
                modifier = Modifier,
                title = "MySelf",
                value = state.aboutMySelf,
                isEnabled = true,
                onUpdate = viewModel::onUpdateMySelf
            )

            Spacer(modifier = Modifier.height(24.dp))

            CommonButton(
                text = stringResource(id = R.string.registration_save),
                modifier = Modifier
                    .fillMaxWidth(),
                buttonColors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalAppTheme.colors.main,
                    disabledBackgroundColor = LocalAppTheme.colors.bgButtonSecondary,
                ),
                onClick = viewModel::onClickProceed,
            )

            Spacer(modifier = Modifier.height(16.dp))

            CommonButton(
                text = "Change Password",
                modifier = Modifier
                    .fillMaxWidth(),
                buttonColors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalAppTheme.colors.main,
                    disabledBackgroundColor = LocalAppTheme.colors.bgButtonSecondary,
                ),
                onClick = viewModel::onClickChangePassword
            )

        }
    }
}