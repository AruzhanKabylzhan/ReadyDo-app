package kz.rdd.core.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.utils.toStringCompose
import kz.rdd.core.ui.widgets.tooltip.TooltipBasicState
import kz.rdd.core.ui.widgets.tooltip.TooltipController
import kz.rdd.core.ui.widgets.tooltip.TooltipPopup

@Composable
fun TooltipProvider(
    tooltipController: TooltipController,
) {
    val arrowHeight = 6.dp

    val topMargin = with(LocalDensity.current) {
        8.dp.plus(arrowHeight).toPx()
    }

    val tooltipState = tooltipController.tooltipState.collectAsStateWithLifecycle()
    val tooltipPosition = tooltipController.tooltipPosition.collectAsStateWithLifecycle()

    val state = tooltipState.value
    val position = tooltipPosition.value

    if (state is TooltipBasicState.Visible) {
        TooltipPopup(
            onDismissRequest = {
                tooltipController.setTooltipState(TooltipBasicState.Gone)
            },
            arrowHeight = arrowHeight,
            position = position.copy(
                offset = position.offset.copy(
                    y = position.offset.y - topMargin.toInt()
                )
            ),
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Text(
                    text = state.text.toStringCompose(),
                    color = LocalAppTheme.colors.white,
                    style = LocalAppTheme.typography.l12,
                )
            }
        }
    }
}
