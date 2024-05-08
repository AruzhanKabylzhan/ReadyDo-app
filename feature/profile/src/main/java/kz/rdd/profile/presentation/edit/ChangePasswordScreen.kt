package kz.rdd.profile.presentation.edit

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.base.SheetDestination
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.ext.rememberKeyboardOpenState
import kz.rdd.core.ui.ext.safeNavigationPadding
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CommonButton
import kz.rdd.core.ui.widgets.CommonTextField
import org.koin.androidx.compose.getViewModel

@Parcelize
class ChangePasswordSheetDestination : SheetDestination {
    @Composable
    override fun Content(controller: DestinationController) {
        ChangePasswordScreen()
    }
}

@Composable
fun ChangePasswordScreen() {
    val viewModel = getViewModel<ChangePasswordViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event)

    val isKeyboardOpen by rememberKeyboardOpenState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(LocalAppTheme.colors.bg)
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .safeNavigationPadding()
    ) {
        Text(
            text = "Change Password",
            style = LocalAppTheme.typography.l20,
            color = LocalAppTheme.colors.primaryText
        )

        Spacer(modifier = Modifier.height(16.dp))

        CommonTextField(
            value = state.password,
            onUpdate = viewModel::onUpdatePassword,
            placeholderText = "Password"
        )
        Spacer(modifier = Modifier.height(10.dp))

        CommonTextField(
            value = state.password2,
            onUpdate = viewModel::onUpdatePassword2,
            placeholderText = "Repeat Password"
        )

        Spacer(modifier = Modifier.height(10.dp))

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

        val spaceBottom by animateDpAsState(
            if (isKeyboardOpen) 250.dp else 36.dp
        )
        Spacer(modifier = Modifier.height(spaceBottom))
    }
}