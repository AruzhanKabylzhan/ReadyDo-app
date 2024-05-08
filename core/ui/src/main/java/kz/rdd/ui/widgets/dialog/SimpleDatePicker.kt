package kz.rdd.core.ui.widgets.dialog

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CommonButton
import kz.rdd.core.ui.widgets.animated.RotatedReveal
import kz.rdd.core.utils.ext.epochToLocalDate
import java.time.Instant
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
val datePickerColors @Composable get() = DatePickerDefaults.colors(
    containerColor = LocalAppTheme.colors.white,
    titleContentColor = LocalAppTheme.colors.primaryText,
    weekdayContentColor = LocalAppTheme.colors.accentText,
    yearContentColor = LocalAppTheme.colors.accentText,
    currentYearContentColor = LocalAppTheme.colors.accentText,
    dayContentColor = LocalAppTheme.colors.primaryText,
    selectedDayContentColor = LocalAppTheme.colors.white,
    disabledDayContentColor = LocalAppTheme.colors.accentText,
    disabledSelectedDayContainerColor = LocalAppTheme.colors.bg1,
    disabledSelectedDayContentColor = LocalAppTheme.colors.accentText,
    selectedDayContainerColor = LocalAppTheme.colors.primaryText,
    todayDateBorderColor = LocalAppTheme.colors.stroke,
    todayContentColor = LocalAppTheme.colors.primaryText,
    dayInSelectionRangeContainerColor = LocalAppTheme.colors.primaryText.copy(alpha = 0.1f)
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SimpleDateRangePickerDialog(
    onDismiss: () -> Unit,
    onDateRangePicked: (LocalDate, LocalDate) -> Unit,
) {
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = Instant.now().toEpochMilli(),
        yearRange = IntRange(2020, LocalDate.now().year),
        initialDisplayMode = DisplayMode.Picker,
    )
    val startDate = remember(dateRangePickerState.selectedStartDateMillis) {
        dateRangePickerState.selectedStartDateMillis?.epochToLocalDate()
    }

    val endDate = remember(dateRangePickerState.selectedEndDateMillis) {
        dateRangePickerState.selectedEndDateMillis?.epochToLocalDate()
    }
    RotatedReveal {
        Column(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 0.dp)
                .animateContentSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            DatePickerDialog(
                shape = RoundedCornerShape(16.dp),
                onDismissRequest = onDismiss,
                confirmButton = {
                    // Seems broken at the moment with DateRangePicker
                    // Works fine with DatePicker
                },
                tonalElevation = 0.dp,
            ) {

                DateRangePicker(
                    modifier = Modifier.weight(1f, fill = false),
                    state = dateRangePickerState,
                    headline = null,
                    title = null,
                    colors = datePickerColors
                )

                CommonButton(
                    text = stringResource(id = R.string.common_apply),
                    isEnabled = startDate != null && endDate != null,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .fillMaxWidth(),
                    onClick = {
                        if (startDate != null && endDate != null) {
                            onDismiss()
                            onDateRangePicked(startDate, endDate)
                        }
                    }
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SimpleDatePickerDialog(
    onDismiss: () -> Unit,
    onDatePicked: (LocalDate) -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli(),
        yearRange = IntRange(2020, LocalDate.now().year),
        initialDisplayMode = DisplayMode.Picker,
    )
    val date = remember(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.epochToLocalDate()
    }
    RotatedReveal {
        Column(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 0.dp)
                .animateContentSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            DatePickerDialog(
                shape = RoundedCornerShape(16.dp),
                onDismissRequest = onDismiss,
                confirmButton = {
                    // Seems broken at the moment with DateRangePicker
                    // Works fine with DatePicker
                },
                tonalElevation = 0.dp,
            ) {

                DatePicker(
                    modifier = Modifier.weight(1f, fill = false),
                    state = datePickerState,
                    headline = null,
                    title = null,
                    colors = datePickerColors
                )

                CommonButton(
                    text = stringResource(id = R.string.common_apply),
                    isEnabled = date != null,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .fillMaxWidth(),
                    onClick = {
                        if (date != null) {
                            onDismiss()
                            onDatePicked(date)
                        }
                    }
                )
            }
        }
    }
}