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
import kz.rdd.core.ui.utils.motion.MaterialSharedAxisX
import kz.rdd.core.ui.widgets.CommonButton
import kz.rdd.core.ui.widgets.dialog.datePickerColors
import kz.rdd.core.utils.ext.epochToLocalDate
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
val TimePickerDefaultColors @Composable get() = run {
    val primaryText = LocalAppTheme.colors.primaryText
    val accentText = LocalAppTheme.colors.accentText
    val bg = LocalAppTheme.colors.bg
    val white = LocalAppTheme.colors.white
    TimePickerDefaults.colors(
        clockDialColor = bg,
        clockDialSelectedContentColor = white,
        clockDialUnselectedContentColor = primaryText,
        selectorColor = primaryText,
        containerColor = bg,
        periodSelectorBorderColor = primaryText,
        periodSelectorSelectedContainerColor = primaryText,
        periodSelectorUnselectedContainerColor = accentText,
        periodSelectorSelectedContentColor = white,
        periodSelectorUnselectedContentColor = accentText,
        timeSelectorSelectedContainerColor = primaryText,
        timeSelectorUnselectedContainerColor = accentText,
        timeSelectorSelectedContentColor = white,
        timeSelectorUnselectedContentColor = white,
    )
}

@Parcelize
class SingleTimePickerDialogDestination : DialogDestination {

    companion object {
        const val RESULT_KEY = "SingleTimePickerDialogDestination"
    }

    @Parcelize
    data class ResultValue(
        val date: LocalTime
    ) : Parcelable

    @Composable
    override fun Content(controller: DestinationController) {
        val timePickerState = rememberTimePickerState()

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
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .fillMaxWidth(),
            ) {
                CommonButton(
                    text = stringResource(id = R.string.common_apply),
                    isEnabled = true,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        scope.launch {
                            controller.returnToPrevDestination(
                                RESULT_KEY, ResultValue(
                                    LocalTime.of(
                                        timePickerState.hour,
                                        timePickerState.minute
                                    )
                                )
                            )
                            controller.dialogNavController.pop()
                        }
                    }
                )
            }
        }
    }
}
