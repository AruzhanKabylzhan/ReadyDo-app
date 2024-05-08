package kz.rdd.login.presentation.forgot_password

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.ext.rememberKeyboardOpenState
import kz.rdd.core.ui.ext.safeNavigationPadding
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CommonButton
import kz.rdd.core.ui.widgets.CommonTextField
import org.koin.androidx.compose.getViewModel

@Parcelize
class ForgotPasswordDestination : Destination {
    @Composable
    override fun Content(controller: DestinationController) {
        ForgotPasswordScreen()
    }
}

@Composable
fun ForgotPasswordScreen(){
    val viewModel = getViewModel<ForgotPasswordViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ComposeEventHandler(
        event = viewModel.event,
    )

    ComposeEffectHandler(
        effect = viewModel.effect,
    )

    val isKeyboardOpen by rememberKeyboardOpenState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .safeNavigationPadding(),
    ) {

        Spacer(modifier = Modifier.height(54.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.login_forgot_password),
                style = LocalAppTheme.typography.h2,
                color = LocalAppTheme.colors.primaryText,
            )

            Spacer(modifier = Modifier.height(24.dp))

            CommonTextField(
                value = state.email,
                placeholderText = "Email",
                onUpdate = {
                    viewModel.onUpdateEmail(it)
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            CommonButton(
                text = stringResource(id = R.string.registration_next),
                modifier = Modifier.fillMaxWidth(),
                buttonColors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalAppTheme.colors.main,
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

            val spaceBottom by animateDpAsState(
                if (isKeyboardOpen) 0.dp else 36.dp
            )
            Spacer(modifier = Modifier.height(spaceBottom))
        }
    }
}