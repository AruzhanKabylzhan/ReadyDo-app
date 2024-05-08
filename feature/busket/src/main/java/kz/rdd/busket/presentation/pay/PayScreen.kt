package kz.rdd.busket.presentation.pay

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import kz.rdd.busket.presentation.BusketViewModel
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.ext.safeNavigationPadding
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CenteredToolbar
import kz.rdd.core.ui.widgets.CommonButton
import kz.rdd.core.ui.widgets.CommonDivider
import kz.rdd.core.ui.widgets.CommonTextField
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Parcelize
class PayDestination(
    private val busketId: Int,
    private val total: Int,
) : Destination {
    @Composable
    override fun Content(controller: DestinationController) {
        val viewModel = getViewModel<PayViewModel>{
            parametersOf(busketId, total)
        }
        PayScreen(viewModel)
    }
}

@Composable
fun PayScreen(
    viewModel: PayViewModel
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppTheme.colors.bg1),
    ) {
        CenteredToolbar(
            title = "Order",
            titleStyle = LocalAppTheme.typography.l17,
            isNavigationIconVisible = true,
            onNavigationIconClick = viewModel::navigateBack
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Total",
                        style = LocalAppTheme.typography.l15,
                        color = LocalAppTheme.colors.primaryText
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = state.total.toString().plus("$"),
                        style = LocalAppTheme.typography.l15,
                        color = LocalAppTheme.colors.primaryText
                    )
                }

                CommonDivider()

                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Delivery",
                        style = LocalAppTheme.typography.l15,
                        color = LocalAppTheme.colors.primaryText
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "2$",
                        style = LocalAppTheme.typography.l15,
                        color = LocalAppTheme.colors.primaryText
                    )
                }

                CommonDivider()

                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Address",
                        style = LocalAppTheme.typography.l15,
                        color = LocalAppTheme.colors.primaryText
                    )
                    Text(
                        text = state.address,
                        style = LocalAppTheme.typography.l16B,
                        color = LocalAppTheme.colors.primaryText
                    )
                }

                CommonDivider()

                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Phone",
                        style = LocalAppTheme.typography.l15,
                        color = LocalAppTheme.colors.primaryText
                    )
                    Text(
                        text = state.phone,
                        style = LocalAppTheme.typography.l16B,
                        color = LocalAppTheme.colors.primaryText
                    )
                }

                CommonDivider()

                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Comment to Delivery",
                        style = LocalAppTheme.typography.l15,
                        color = LocalAppTheme.colors.primaryText
                    )
                    CommonTextField(
                        value = state.comment,
                        onUpdate = viewModel::onUpdateComment,
                        placeholderText = "Comment"
                    )
                }

                CommonDivider()

                Row(
                    modifier = Modifier
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    val color2 = if(state.method == 2) LocalAppTheme.colors.secondary else LocalAppTheme.colors.white
                    val textColor2 = if(state.method == 2) LocalAppTheme.colors.white else LocalAppTheme.colors.secondary
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(color2, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        CommonButton(
                            text = "CASH",
                            onClick = { viewModel.onChangeMethod(2) },
                            textColor = textColor2,
                            buttonColors = ButtonDefaults.buttonColors(
                                backgroundColor = color2,
                                disabledBackgroundColor = LocalAppTheme.colors.bgButtonSecondary,
                            )
                        )
                    }
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        LocalAppTheme.colors.main,
                        RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp)
                    )
                    .background(LocalAppTheme.colors.white)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Total",
                        style = LocalAppTheme.typography.l15,
                        color = LocalAppTheme.colors.primaryText
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = state.total.toString().plus("$"),
                        style = LocalAppTheme.typography.l15,
                        color = LocalAppTheme.colors.primaryText
                    )
                }

                CommonButton(
                    text = "Pay",
                    onClick = viewModel::onClickPay
                )

                Spacer(modifier = Modifier.safeNavigationPadding())
            }
        }
    }
}