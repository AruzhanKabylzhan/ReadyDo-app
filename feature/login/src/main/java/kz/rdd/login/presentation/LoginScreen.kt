package kz.rdd.login.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.ext.rememberKeyboardOpenState
import kz.rdd.core.ui.ext.safeNavigationPadding
import kz.rdd.core.ui.ext.safeStatusBarPadding
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.utils.connectNode
import kz.rdd.core.ui.widgets.CommonButton
import kz.rdd.core.ui.widgets.CommonPasswordTextField
import kz.rdd.core.ui.widgets.CommonTextField
import kz.rdd.core.ui.widgets.PhoneTextField
import kz.rdd.login.presentation.introduction.IntroductionScreen
import kz.rdd.core.ui.R as uiR
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(
    controller: DestinationController
) {
    val viewModel = getViewModel<LoginViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ComposeEventHandler(
        event = viewModel.event,
        controller = controller,
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

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {

            Spacer(modifier = Modifier.height(54.dp))

            Text(
                text = "ReadyDo",
                style = LocalAppTheme.typography.h1,
                color = LocalAppTheme.colors.main,
            )
            Text(
                text = "with us",
                style = LocalAppTheme.typography.h2.copy(
                    fontWeight = FontWeight.W400
                ),
                color = LocalAppTheme.colors.main,
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

            CommonPasswordTextField(
                value = state.password,
                onUpdate = {
                    viewModel.onUpdatePassword(it)
                },
            )

            Spacer(modifier = Modifier.height(24.dp))

            CommonButton(
                text = "Sign in",
                modifier = Modifier.fillMaxWidth(),
                buttonColors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalAppTheme.colors.secondary,
                    disabledBackgroundColor = LocalAppTheme.colors.bgButtonSecondary,
                ),
                onClick = viewModel::onClickProceed,
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = viewModel::onClickForgotPassword,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Forgot my password",
                    style = LocalAppTheme.typography.l12.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = LocalAppTheme.colors.accentText,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            RegistrationButton(
                onClick = viewModel::onClickRegistration
            )

            val spaceBottom by animateDpAsState(
                if (isKeyboardOpen) 0.dp else 36.dp
            )
            Spacer(modifier = Modifier.height(spaceBottom))
        }
    }
}


@Composable
private fun RegistrationButton(onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row {
            Text(
                text = "Don\\'t have an account?".uppercase(),
                style = LocalAppTheme.typography.l12.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = LocalAppTheme.colors.accentText,
            )

            Text(
                text = " Registration".uppercase(),
                style = LocalAppTheme.typography.l12.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = LocalAppTheme.colors.primaryText,
            )
        }
    }
}