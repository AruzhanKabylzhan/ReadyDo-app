package kz.rdd.busket.presentation.pay

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.R
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.base.SheetDestination
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.ext.safeNavigationPadding
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CommonButton
import kz.rdd.core.ui.widgets.CommonDivider
import kz.rdd.store.UserCard
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Parcelize
class PaySheetDestination(
    private val busketId: Int,
) : SheetDestination {

    override val dismissByTouch: Boolean
        get() = true

    @Composable
    override fun Content(controller: DestinationController) {
        val viewModel = getViewModel<PaySheetViewModel>{
            parametersOf(busketId)
        }
        PauSheetScreen(viewModel)
    }
}

@Composable
fun PauSheetScreen(
    viewModel: PaySheetViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event)

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(LocalAppTheme.colors.bg)
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .safeNavigationPadding()
    ) {
        Text(
            text = "Payment via card",
            style = LocalAppTheme.typography.l20,
            color = LocalAppTheme.colors.primaryText
        )

        Spacer(modifier = Modifier.height(20.dp))

        state.cards.forEach {
            Spacer(modifier = Modifier.height(7.dp))
            CardComponent(
                userCard = it,
                isChecked = state.selectedCard == it,
                onClickCard = viewModel::onClickCard,
            )
            Spacer(modifier = Modifier.height(7.dp))
            CommonDivider()
        }

        Spacer(modifier = Modifier.height(20.dp))

        CommonButton(
            text = "Link a new card",
            onClick = viewModel::onClickAddCard
        )

        Spacer(modifier = Modifier.height(5.dp))

        CommonButton(
            text = "Pay",
            onClick = viewModel::onClickPay
        )
    }
}

@Composable
fun CardComponent(
    userCard: UserCard,
    isChecked: Boolean,
    onClickCard: (UserCard) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .scaledClickable { onClickCard(userCard) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_visa_card),
            contentDescription = ""
        )

        Spacer(modifier = Modifier.width(18.dp))

        Text(
            text = "VISA ... ".plus(userCard.cardNum.toString().substring(0, 4)),
            style = LocalAppTheme.typography.l15,
            color = LocalAppTheme.colors.primaryText
        )

        Spacer(modifier = Modifier.weight(1f))

        CheckboxContent(isChecked)
    }
}

@Composable
fun CheckboxContent(
    isChecked: Boolean,
) {
    val bg by animateColorAsState(LocalAppTheme.colors.run {
        if(isChecked) checkGreen else white
    })
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(RoundedCornerShape(7.dp))
            .border(
                if (isChecked) 0.dp else 1.dp,
                LocalAppTheme.colors.stroke,
                RoundedCornerShape(7.dp)
            )
            .background(bg),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = isChecked) {
            androidx.compose.material.Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_check_16),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    }
}

