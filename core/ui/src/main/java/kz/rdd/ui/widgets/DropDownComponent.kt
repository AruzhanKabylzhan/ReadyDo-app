package kz.rdd.core.ui.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.ext.alphaClickable
import kz.rdd.core.ui.ext.clickableWithIndication
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.utils.SelectableItem
import kz.rdd.core.ui.utils.toStringCompose

@Composable
fun DropdownComponent(
    title: String,
    placeholder: String,
    selectableItem: SelectableItem?,
    itemList: List<SelectableItem>,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalAppTheme.typography.l12B,
    hasError: Boolean = false,
    onSelect: (selectableItem: SelectableItem) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxWidth(),
    ) {
        val isExpandedChooser = remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(LocalAppTheme.colors.white)
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
        ) {
            Text(
                text = title.uppercase(),
                style = textStyle,
                color = LocalAppTheme.colors.accentText,
            )

            ChooserArrowContainer(
                item = selectableItem,
                isExpanded = isExpandedChooser,
                hasError = hasError,
                placeholder = placeholder,
            )

            ErrorText(hasError = hasError)
        }

        DropdownContainer(
            isExpanded = isExpandedChooser.value,
            onDismiss = { isExpandedChooser.value = false },
            maxWidth = maxWidth,
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, LocalAppTheme.colors.stroke2, RoundedCornerShape(10.dp))
            ) {
                itemList.forEachIndexed { index, item ->
                    DropdownItem(
                        text = item.name.toStringCompose(),
                        isChecked = selectableItem?.id == item.id,
                    ) {
                        onSelect(item)
                        isExpandedChooser.value = false
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownContainer(
    isExpanded: Boolean,
    onDismiss: () -> Unit,
    maxWidth: Dp,
    content: @Composable ColumnScope.() -> Unit
) {
    MaterialTheme(
        shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(0.dp)),
    ) {
        CommonDropdownMenu(
            expanded = isExpanded,
            offset = DpOffset(0.dp, 0.dp),
            onDismissRequest = onDismiss,
            modifier = Modifier
                .width(maxWidth)
                .heightIn(max = 200.dp)
                .commonDropDownMenuModifier(
                    paddingBottom = 10.dp,
                    paddingTop = 10.dp,
                    paddingStart = 10.dp,
                    paddingEnd = 10.dp,
                ),
            content = {
                Column(
                    modifier = Modifier
                        .heightIn(max = 200.dp)
                        .background(LocalAppTheme.colors.white)
                        .verticalScroll(rememberScrollState()),
                    content = content,
                )
            },
        )
    }
}


@Composable
fun DropdownItem(
    text: String,
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor = if (isChecked) {
        LocalAppTheme.colors.primaryText
    } else {
        LocalAppTheme.colors.white
    }

    val textColor = if (isChecked) {
        LocalAppTheme.colors.white
    } else {
        LocalAppTheme.colors.primaryText
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp)
            .background(backgroundColor)
            .clickableWithIndication(onClick = onClick)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            color = textColor,
            style = LocalAppTheme.typography.l16,
        )
        CommonDivider(
            color = LocalAppTheme.colors.stroke2,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ErrorText(
    hasError: Boolean = false,
) {
    AnimatedVisibility(visible = hasError) {
        Text(
            text = stringResource(id = R.string.common_error_required_field),
            style = LocalAppTheme.typography.l12,
            color = LocalAppTheme.colors.error,
            textAlign = TextAlign.Start,
        )
    }
}

@Composable
fun ChooserArrowContainer(
    item: SelectableItem?,
    placeholder: String,
    hasError: Boolean = false,
    isExpanded: MutableState<Boolean>,
    showArrow: Boolean = true,
) {
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded.value) 180f else 0f,
        label = ""
    )
    CommonChooserContainer(
        item = item,
        placeholder = placeholder,
        hasError = hasError,
        onClick = {
            isExpanded.value = !isExpanded.value && showArrow
        },
        trailing = {
            if (showArrow) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_chevron_bottom),
                    contentDescription = null,
                    tint = LocalAppTheme.colors.stroke,
                    modifier = Modifier.rotate(rotation)
                )
            }
        }
    )
}

@Composable
fun CommonChooserContainer(
    item: SelectableItem?,
    placeholder: String,
    hasError: Boolean = false,
    onClick: (SelectableItem?) -> Unit,
    trailing: @Composable () -> Unit,
) {
    val (text, color) = if (item != null) {
        item.name.toStringCompose() to LocalAppTheme.colors.primaryText
    } else {
        placeholder to LocalAppTheme.colors.accentText
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .alphaClickable(onClick = {
                onClick(item)
            })
            .border(1.dp, LocalAppTheme.colors.run {
                if (hasError) error else stroke2
            }, RoundedCornerShape(10.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = LocalAppTheme.typography.l16,
            color = color,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        trailing()
    }
}
