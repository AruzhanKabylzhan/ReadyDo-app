@file:OptIn(
    ExperimentalMaterial3Api::class,
)

package kz.rdd.core.ui.screen.calendar

import android.os.Parcelable
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
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
import kz.rdd.core.ui.utils.VmRes
import kz.rdd.core.ui.utils.motion.MaterialSharedAxisX
import kz.rdd.core.ui.utils.toStringCompose
import kz.rdd.core.ui.widgets.CommonButton
import kz.rdd.core.ui.widgets.dialog.datePickerColors
import kz.rdd.core.utils.ext.epochToLocalDate
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

@Parcelize
class SingleDateTimePickerDialogDestination(
    private val title: VmRes.Parcelable<CharSequence>? = null,
    private val startDate: LocalDate? = null,
    private val selectableDates: Set<LocalDate>? = null
) : DialogDestination {

    companion object {
        const val RESULT_KEY = "SingleDateTimePickerDialogDestination"
    }

    @Parcelize
    data class ResultValue(
        val date: LocalDateTime
    ) : Parcelable

    private enum class Selection {
        DATE,
        TIME,
    }

    @Composable
    override fun Content(controller: DestinationController) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = startDate
                ?.atTime(LocalTime.MAX)
                ?.toInstant(OffsetDateTime.now().offset)
                ?.toEpochMilli()
                ?: Instant.now().toEpochMilli(),
            yearRange = selectableDates?.takeIf {
                it.isNotEmpty()
            }?.let {
                IntRange(it.first().year, it.last().year)
            } ?: IntRange(2020, LocalDate.now().year),
            initialDisplayMode = DisplayMode.Picker,
        )
        val timePickerState = rememberTimePickerState()
        val date = remember(datePickerState.selectedDateMillis) {
            datePickerState.selectedDateMillis?.epochToLocalDate()
        }

        val selection = remember {
            mutableStateOf(Selection.DATE)
        }
        val isDateValid = remember(date) {
            date?.let(::validateDate) ?: false
        }

        val scope = rememberCoroutineScope()
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(LocalAppTheme.colors.white)
                .padding(vertical = 10.dp, horizontal = 0.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            title?.let {
                Text(
                    text = it.toStringCompose(),
                    style = LocalAppTheme.typography.l14B,
                    color = LocalAppTheme.colors.primaryText,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            val content: @Composable AnimatedVisibilityScope.(Selection) -> Unit = remember {
                {
                    when(it) {
                        Selection.DATE -> {
                            DatePicker(
                                modifier = Modifier,
                                dateValidator = ::validateDate,
                                state = datePickerState,
                                headline = null,
                                title = null,
                                showModeToggle = false,
                                colors = datePickerColors
                            )
                        }
                        Selection.TIME -> {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .padding(top = 10.dp)
                            ) {
                                TimePicker(
                                    state = timePickerState,
                                    colors = TimePickerDefaultColors,
                                )
                            }
                        }
                    }
                }
            }
            MaterialSharedAxisX(
                targetState = selection.value,
                forward = false,
                content = content,
            )
            Spacer(modifier = Modifier.height(8.dp))

            val onClickBack = {
                selection.value = Selection.DATE
            }
            val onClickNext = {
                when(selection.value) {
                    Selection.DATE -> {
                        selection.value = Selection.TIME
                    }
                    Selection.TIME -> {
                        if (date != null) {
                            scope.launch {
                                controller.returnToPrevDestination(
                                    RESULT_KEY, ResultValue(
                                        date.atTime(timePickerState.hour, timePickerState.minute))
                                )
                                controller.dialogNavController.pop()
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .fillMaxWidth(),
            ) {
                if (selection.value == Selection.TIME) {
                    CommonButton(
                        text = stringResource(id = R.string.common_back),
                        modifier = Modifier.weight(1f),
                        buttonColors = ButtonDefaults.buttonColors(
                            backgroundColor = LocalAppTheme.colors.white,
                            disabledBackgroundColor = LocalAppTheme.colors.bgButtonSecondary,
                        ),
                        textColor = LocalAppTheme.colors.primaryText,
                        border = BorderStroke(1.dp, LocalAppTheme.colors.accentText),
                        onClick = onClickBack
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                CommonButton(
                    text = when(selection.value) {
                        Selection.DATE -> stringResource(id = R.string.common_continue)
                        Selection.TIME -> stringResource(id = R.string.common_apply)
                    },
                    isEnabled = when(selection.value) {
                        Selection.DATE -> isDateValid
                        Selection.TIME -> true
                    },
                    modifier = Modifier.weight(1f),
                    onClick = onClickNext
                )
            }
        }
    }

    private fun validateDate(epoch: Long): Boolean {
        val calendarDate = epoch.epochToLocalDate()
        return validateDate(calendarDate)
    }

    private fun validateDate(date: LocalDate): Boolean {
        selectableDates?.let {
            return date in it
        }
        if (startDate == null) return true
        return date >= startDate
    }
}
