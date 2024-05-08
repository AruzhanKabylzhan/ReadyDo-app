package kz.rdd.busket.presentation.pay

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.base.SheetDestination
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.ext.rememberKeyboardOpenState
import kz.rdd.core.ui.ext.safeNavigationPadding
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CommonButton
import kz.rdd.core.ui.widgets.CommonDivider
import kz.rdd.core.ui.widgets.CommonTextFieldWithTitle
import org.koin.androidx.compose.getViewModel

@Parcelize
class AddCardSheetDestination : SheetDestination {
    @Composable
    override fun Content(controller: DestinationController) {
        AddCardScreen()
    }
}

@Composable
fun AddCardScreen() {
    val viewModel = getViewModel<AddCardViewModel>()
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
            text = "New card",
            style = LocalAppTheme.typography.l20,
            color = LocalAppTheme.colors.primaryText
        )

        CommonTextFieldWithTitle(
            title = "Card Number",
            value = state.cardNum,
            onUpdate = viewModel::onUpdateCardNum,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        CommonDivider()

        CommonTextFieldWithTitle(
            title = "Vadility",
            value = state.date,
            onUpdate = viewModel::onUpdateDate
        )

        CommonDivider()

        CommonTextFieldWithTitle(
            title = "CVV",
            value = state.cvv,
            onUpdate = viewModel::onUpdateCvv,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        CommonButton(
            text = "Link",
            onClick = viewModel::onClickLink
        )

        val spaceBottom by animateDpAsState(
            if (isKeyboardOpen) 350.dp else 36.dp
        )
        Spacer(modifier = Modifier.height(spaceBottom))
    }
}