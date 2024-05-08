package kz.rdd.core.ui.screen.item_picker

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.LocalDestinationController
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.ext.alphaClickable
import kz.rdd.core.ui.ext.backgroundShimmer
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.ext.returnToPrevDestination
import kz.rdd.core.ui.ext.safeNavigationPadding
import kz.rdd.core.ui.ext.withShimmer
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.utils.toStringCompose
import kz.rdd.core.ui.widgets.CheckBox
import kz.rdd.core.ui.widgets.CommonButton

@Composable
internal fun SheetPickerContainer(
    viewModel: SheetPickerViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val controller = LocalDestinationController

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event) {
        when(this) {
            is ReturnToPrevDestinationAndExitEvent -> {
                controller.returnToPrevDestination(data)
                controller.navigateBack()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(LocalAppTheme.colors.bg1)
            .safeNavigationPadding()
            .then(modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = state.title.toStringCompose(),
                style = LocalAppTheme.typography.l17B,
                color = LocalAppTheme.colors.primaryText,
                modifier = Modifier.weight(1f),
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_close_small_24),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.alphaClickable(
                    onClick = viewModel::navigateBack
                )
            )
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .weight(1f, fill = false)
                .clip(RoundedCornerShape(8.dp))
                .background(LocalAppTheme.colors.white)
                .animateContentSize()
        ) {
            Crossfade(
                targetState = state.isLoading,
                label = "",
                modifier = Modifier.fillMaxWidth(),
            ) { isLoading ->
                if (isLoading) {
                    ShimmerContainer()
                } else {
                    ItemContainer(
                        items = state.items,
                        action = state.action,
                        onClickItem = viewModel::onClickItem,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.action == SheetPickerAction.MULTIPLE) {
            CommonButton(
                text = stringResource(id = R.string.common_done),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                onClick = viewModel::onClickAction,
            )
        }
    }
}

@Composable
private fun ItemDivider() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .background(LocalAppTheme.colors.bg1)
    )
}

@Composable
private fun ShimmerContainer() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val shimmerCount = 4
        repeat(shimmerCount) {
            ShimmerItem()
            if (it != shimmerCount - 1) {
                ItemDivider()
            }
        }
    }
}

@Composable
private fun ShimmerItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Spacer(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(0.7f)
                .height(20.dp)
                .backgroundShimmer()
                .withShimmer()
        )
    }
}

@Composable
private fun ItemContainer(
    items: List<SheetPickerItem>,
    action: SheetPickerAction,
    onClickItem: (SheetPickerItem) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        items.forEachIndexed { index, item ->
            Item(
                item = item,
                action = action,
                onClickItem = onClickItem,
            )
            if (index != items.lastIndex) {
                ItemDivider()
            }
        }
    }
}

@Composable
private fun Item(
    item: SheetPickerItem,
    action: SheetPickerAction,
    onClickItem: (SheetPickerItem) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .alphaClickable {
                onClickItem(item)
            }
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = item.item.itemName.toStringCompose(),
            style = LocalAppTheme.typography.l16,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier.weight(1f),
        )
        if (action == SheetPickerAction.MULTIPLE) {
            CheckBox(
                isChecked = item.isSelected,
                isCircle = false,
            )
        }
    }
}