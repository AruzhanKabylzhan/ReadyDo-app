@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package kz.rdd.core.ui.screen.calendar

import android.os.Parcelable
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.pop
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.DialogDestination
import kz.rdd.core.ui.ext.returnToPrevDestination
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CommonButton
import kz.rdd.core.ui.widgets.dialog.datePickerColors
import kz.rdd.core.utils.ext.epochToLocalDate
import java.time.Instant
import java.time.LocalDate

@Parcelize
class RangeDatePickerDialogDestination(
    private val startRangeYear: Int = 2022,
    private val limitDate: LocalDate? = null,
) : DialogDestination {

    companion object {
        const val RESULT_KEY = "RangeDatePickerDialogDestination"
    }

    @Parcelize
    data class ResultValue(
        val startDate: LocalDate,
        val endDate: LocalDate
    ) : Parcelable

    @Composable
    override fun Content(controller: DestinationController) {
        val dateRangePickerState = rememberDateRangePickerState(
            initialSelectedStartDateMillis = Instant.now().toEpochMilli(),
            yearRange = IntRange(startRangeYear, LocalDate.now().year),
            initialDisplayMode = DisplayMode.Picker,
        )
        val startDate = remember(dateRangePickerState.selectedStartDateMillis) {
            dateRangePickerState.selectedStartDateMillis?.epochToLocalDate()
        }

        val endDate = remember(dateRangePickerState.selectedEndDateMillis) {
            dateRangePickerState.selectedEndDateMillis?.epochToLocalDate()
        }
        val scope = rememberCoroutineScope()
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(LocalAppTheme.colors.white)
                .padding(vertical = 10.dp, horizontal = 0.dp)
                .animateContentSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            DateRangePicker(
                modifier = Modifier.weight(1f, fill = false),
                state = dateRangePickerState,
                dateValidator = {
                    if (limitDate == null) return@DateRangePicker true
                    it.epochToLocalDate() <= limitDate
                },
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
                        scope.launch {
                            controller.returnToPrevDestination(
                                RESULT_KEY,
                                ResultValue(startDate, endDate)
                            )
                            controller.dialogNavController.pop()
                        }
                    }
                }
            )
        }
    }
}
