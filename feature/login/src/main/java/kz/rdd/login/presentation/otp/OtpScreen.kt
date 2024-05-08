@file:OptIn(ExperimentalAnimationApi::class, ExperimentalLayoutApi::class)

package kz.rdd.login.presentation.otp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.NavAction
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.base.navigation.NavigationAnimation
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.ext.safeNavigationPadding
import kz.rdd.core.ui.ext.safeStatusBarPadding
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.utils.toStringCompose
import kz.rdd.core.ui.widgets.AnimatedCounter
import kz.rdd.core.ui.widgets.CommonButton
import kz.rdd.core.ui.widgets.OtpTextField
import kz.rdd.core.ui.widgets.Toolbar
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import kz.rdd.core.ui.R as uiR

@Parcelize
class OtpDestination(
    private val params: OtpParams,
) : Destination {

    @Composable
    override fun Content(controller: DestinationController) {
        val viewModel = getViewModel<OtpViewModel> {
            parametersOf(params)
        }
        OtpScreen(viewModel)
    }

    override fun openTransition(action: NavAction, from: Destination?): ContentTransform {
        return NavigationAnimation.slideHorizontalFromEnd()
    }

    override fun closeTransition(to: Destination?): ContentTransform {
        return NavigationAnimation.slideHorizontalFromStart()
    }
}

@Composable
private fun OtpScreen(viewModel: OtpViewModel = getViewModel()) {

    ComposeEventHandler(event = viewModel.event)

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .safeNavigationPadding()
            .safeStatusBarPadding()
            .imePadding()
    ) {

        Toolbar(
            title = "",
            onNavigationIconClick = viewModel::navigateBack,
            isNavigationIconVisible = true,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp)
        ) {
            Content(state, viewModel)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            AnimatedVisibility(
                visible = state.value.counter == -1,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.scaledClickable(
                            onClick = { }
                        )
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = uiR.drawable.ic_retry_20),
                            contentDescription = null,
                            tint = LocalAppTheme.colors.iconBlack
                        )
                        Text(
                            text = stringResource(id = uiR.string.otp_retry),
                            style = LocalAppTheme.typography.l14,
                            color = LocalAppTheme.colors.primaryText,
                            textAlign = TextAlign.Center,
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }


            AnimatedVisibility(
                visible = state.value.canProceed,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    CommonButton(
                        text = stringResource(id = uiR.string.common_continue),
                        modifier = Modifier.fillMaxWidth(),
                        isLoading = state.value.isLoading,
                        onClick = viewModel::onClickContinue
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.Content(
    state: androidx.compose.runtime.State<State>,
    viewModel: OtpViewModel
) {
    Spacer(modifier = Modifier.height(32.dp))

    Text(
        text = stringResource(id = uiR.string.otp_title),
        style = LocalAppTheme.typography.h2,
        color = LocalAppTheme.colors.primaryText,
    )

    Spacer(modifier = Modifier.height(24.dp))

    val errorText = state.value.errorText?.toStringCompose().orEmpty()
    val hasError = errorText.isNotEmpty()

    OtpTextField(
        otpText = state.value.otpValue,
        hasError = hasError,
        onOtpTextChange = viewModel::onUpdateOtp
    )

    Spacer(modifier = Modifier.height(16.dp))

    if (hasError) {
        Text(
            text = state.value.errorText?.toStringCompose().orEmpty(),
            style = LocalAppTheme.typography.l14,
            color = LocalAppTheme.colors.error,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    } else {
        Text(
            text = stringResource(id = uiR.string.otp_description),
            style = LocalAppTheme.typography.l14,
            color = LocalAppTheme.colors.accentText,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }

    Spacer(modifier = Modifier.height(32.dp))

    AnimatedVisibility(
        visible = state.value.counter > 0,
        modifier = Modifier.align(Alignment.CenterHorizontally)
    ) {
        FlowRow(
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = uiR.string.otp_action_timer),
                style = LocalAppTheme.typography.l14,
                color = LocalAppTheme.colors.accentText,
            )

            AnimatedCounter(
                text = " " + stringResource(id = uiR.string.common_sec, state.value.counter),
                style = LocalAppTheme.typography.l14.copy(
                    color = LocalAppTheme.colors.primaryText
                ),
            )
        }
    }
}
