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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
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
import java.time.LocalTime
import java.time.ZoneOffset

@Parcelize
class SingleDatePickerDialogDestination(
    private val startDate: LocalDate? = null,
    private val endDate: LocalDate? = null,
    private val firstYear: Int = 2022,
    private val lastYear: Int = LocalDate.now().year,
    private val initialSelectedDate: LocalDate? = null,
    private val additionalKey: String? = null,
) : DialogDestination {

    companion object {
        const val RESULT_KEY = "SingleDatePickerDialogDestination"
    }

    @Parcelize
    data class ResultValue(
        val date: LocalDate
    ) : Parcelable

    @Composable
    override fun Content(controller: DestinationController) {
        val yearRange = IntRange(firstYear, lastYear)
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = initialSelectedDate?.let {
                it.atTime(LocalTime.MIDNIGHT)?.toInstant(ZoneOffset.UTC)?.toEpochMilli()
            } ?: Instant.now().toEpochMilli(),
            yearRange = yearRange,
            initialDisplayMode = DisplayMode.Picker,
        )
        val date = remember(datePickerState.selectedDateMillis) {
            datePickerState.selectedDateMillis?.epochToLocalDate()
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

            DatePicker(
                modifier = Modifier.weight(1f, fill = false),
                dateValidator = {
                    when {
                        startDate == null && endDate == null -> true
                        startDate == null && endDate != null -> {
                            it.epochToLocalDate() <= endDate
                        }
                        startDate != null && endDate == null -> {
                            it.epochToLocalDate() >= startDate
                        }
                        else -> {
                            val currentDate = it.epochToLocalDate()
                            startDate!! <= currentDate && currentDate <= endDate!!
                        }
                    }
                },
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
                        scope.launch {
                            controller.returnToPrevDestination(
                                key = RESULT_KEY,
                                value = ResultValue(date),
                                additionalKey = additionalKey
                            )
                            controller.dialogNavController.pop()
                        }
                    }
                }
            )
        }
    }
}
