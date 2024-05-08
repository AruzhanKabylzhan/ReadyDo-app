package kz.rdd.core.ui.widgets

import androidx.compose.animation.animateContentSize
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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.ext.clickableWithIndication
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.theme.LocalLocaleProvider
import kz.rdd.core.utils.ext.capitalizeWithLocale
import java.time.Month
import java.time.format.TextStyle

@Composable
fun DateChooser(
    month: State<Int?>,
    year: State<Int>,
    years: List<Int>,
    months: List<Int?>,
    onClickMonth: (Int?) -> Unit,
    onClickYear: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxWidth(),
    ) {
        val isExpandedMonth = remember(month.value) {
            mutableStateOf(false)
        }
        val isExpandedYear = remember(year.value) {
            mutableStateOf(false)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(LocalAppTheme.colors.white)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.common_year) + ":",
                color = LocalAppTheme.colors.primaryText,
                style = LocalAppTheme.typography.l16B,
            )
            ChooserContainer(
                text = year.value.toString(),
                isExpanded = isExpandedYear,
                showArrow = years.size > 1
            )
            Text(
                text = stringResource(id = R.string.common_month),
                color = LocalAppTheme.colors.primaryText,
                style = LocalAppTheme.typography.l16B,
            )
            ChooserContainer(
                text = month.value.getMonthText(),
                isExpanded = isExpandedMonth,
            )
        }

        // Year Dropdown
        Dropdown(
            isExpanded = isExpandedYear.value,
            onDismiss = { isExpandedYear.value = false },
            maxWidth = maxWidth,
        ) {
            years.forEach {
                DropdownItem(
                    text = it.toString(),
                    isChecked = year.value == it,
                ) {
                    onClickYear(it)
                }
            }
        }

        // Month Dropdown
        Dropdown(
            isExpanded = isExpandedMonth.value,
            onDismiss = { isExpandedMonth.value = false },
            maxWidth = maxWidth,
        ) {
            months.forEach {
                DropdownItem(
                    text = it.getMonthText(),
                    isChecked = month.value == it,
                ) {
                    onClickMonth(it)
                }
            }
        }
    }
}


@Composable
private fun ChooserContainer(
    text: String,
    isExpanded: MutableState<Boolean>,
    showArrow: Boolean = true,
) {

    val rotation by animateFloatAsState(targetValue = if (isExpanded.value) 180f else 0f)
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .clickableWithIndication(
                bounded = true,
                rippleColor = LocalAppTheme.colors.primaryText,
            ) {
                isExpanded.value = !isExpanded.value && showArrow
            }
            .border(1.dp, LocalAppTheme.colors.stroke, RoundedCornerShape(18.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = LocalAppTheme.typography.l14B,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier.animateContentSize()
        )
        if (showArrow) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_triangle_down_16),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.rotate(rotation)
            )
        }
    }
}

@Composable
private fun Dropdown(
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
            offset = DpOffset(0.dp, 8.dp),
            onDismissRequest = onDismiss,
            modifier = Modifier
                .width(maxWidth)
                .heightIn(max = 200.dp)
                .background(LocalAppTheme.colors.white)
                .border(1.dp, LocalAppTheme.colors.stroke)
                .shadow(elevation = 0.dp),
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
private fun DropdownItem(
    text: String,
    isChecked: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickableWithIndication(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .weight(1f),
                color = LocalAppTheme.colors.black,
                style = LocalAppTheme.typography.l14,
            )
            if (isChecked) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_check_16),
                    contentDescription = null,
                    tint = LocalAppTheme.colors.checkGreen,
                )
            }
        }

        CommonDivider()
    }
}

@Composable
private fun Int?.getMonthText(): String {
    return this?.let { monthValue ->
        Month.of(monthValue)
            .getDisplayName(
                TextStyle.FULL_STANDALONE,
                LocalLocaleProvider.current,
            ).capitalizeWithLocale()
    } ?: stringResource(id = R.string.common_all)
}