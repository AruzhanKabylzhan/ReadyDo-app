package kz.rdd.core.ui.ext

import android.app.DatePickerDialog
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import kz.rdd.core.ui.theme.Colors
import kz.rdd.core.ui.theme.LocalAppTheme
import java.time.LocalDate

@Composable
fun LazyListState.OnBottomReached(
    loadMoreEnabled: Boolean,
    onLoadMore: () -> Unit,
) {
    val isListEnd by remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false
            lastVisibleItem.index >= layoutInfo.totalItemsCount - 1
        }
    }
    val shouldLoadMore = loadMoreEnabled && isListEnd

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) onLoadMore()
    }
}

fun LazyListState.isBottomReached(): Boolean {
    val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
    return lastVisibleItemIndex == layoutInfo.totalItemsCount - 1
}

@Composable
fun rememberDatePicker(
    initialDate: LocalDate = LocalDate.now(),
    onDateChange : (newDate: LocalDate) -> Unit
) : DatePickerDialog {
    val context = LocalContext.current
    val initialYear = initialDate.year
    val initialMonth = initialDate.monthValue - 1
    val initialDayOfMonth = initialDate.dayOfMonth
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year: Int, month: Int, dayOfMonth: Int ->
            val newLocalDate = LocalDate.of(year, month + 1, dayOfMonth)
            onDateChange(newLocalDate)
        },
        initialYear, initialMonth, initialDayOfMonth
    )
    return remember { datePickerDialog }
}

@Composable
fun getGoldenBrush(
    colors: Colors = LocalAppTheme.colors
) = remember(colors) {
    Brush.verticalGradient(
        listOf(
            colors.goldGradientDark,
            colors.goldGradientMedium,
            colors.goldGradientDark,
        )
    )
}

@Composable
fun getSilverBrush(
    colors: Colors = LocalAppTheme.colors
) = remember(colors) {
    Brush.verticalGradient(
        listOf(
            colors.silverGradientDark,
            colors.silverGradientMedium,
            colors.silverGradientDark,
        )
    )
}

@Composable
fun getBronzeBrush(
    colors: Colors = LocalAppTheme.colors
) = remember(colors) {
    Brush.verticalGradient(
        listOf(
            colors.bronzeGradientDark,
            colors.bronzeGradientMedium,
            colors.bronzeGradientDark,
        )
    )
}

@Composable
fun <T> rememberRef(): MutableState<T?> {
    // for some reason it always recreated the value with vararg keys,
    // leaving out the keys as a parameter for remember for now
    return remember() {
        object: MutableState<T?> {
            override var value: T? = null

            override fun component1(): T? = value

            override fun component2(): (T?) -> Unit = { value = it }
        }
    }
}

@Composable
fun <T> rememberPrevious(
    current: T,
    shouldUpdate: (prev: T?, curr: T) -> Boolean = { a: T?, b: T -> a != b },
): T? {
    val ref = rememberRef<T>()

    // launched after render, so the current render will have the old value anyway
    SideEffect {
        if (shouldUpdate(ref.value, current)) {
            ref.value = current
        }
    }

    return ref.value
}

@Composable
fun rememberCurrentOffset(state: LazyListState): State<Int> {
    val position = remember { derivedStateOf { state.firstVisibleItemIndex } }
    val itemOffset = remember { derivedStateOf { state.firstVisibleItemScrollOffset } }
    val lastPosition = rememberPrevious(position.value)
    val lastItemOffset = rememberPrevious(itemOffset.value)
    val currentOffset = remember { mutableStateOf(0) }

    LaunchedEffect(position.value, itemOffset.value) {
        if (lastPosition == null || position.value == 0) {
            currentOffset.value = itemOffset.value
        } else if (lastPosition == position.value) {
            currentOffset.value += (itemOffset.value - (lastItemOffset ?: 0))
        } else if (lastPosition > position.value) {
            currentOffset.value -= (lastItemOffset ?: 0)
        } else { // lastPosition.value < position.value
            currentOffset.value += itemOffset.value
        }
    }

    return currentOffset
}

@Composable
fun rememberCurrentOffset(state: LazyGridState): State<Int> {
    val position = remember { derivedStateOf { state.firstVisibleItemIndex } }
    val itemOffset = remember { derivedStateOf { state.firstVisibleItemScrollOffset } }
    val lastPosition = rememberPrevious(position.value)
    val lastItemOffset = rememberPrevious(itemOffset.value)
    val currentOffset = remember { mutableStateOf(0) }

    LaunchedEffect(position.value, itemOffset.value) {
        if (lastPosition == null || position.value == 0) {
            currentOffset.value = itemOffset.value
        } else if (lastPosition == position.value) {
            currentOffset.value += (itemOffset.value - (lastItemOffset ?: 0))
        } else if (lastPosition > position.value) {
            currentOffset.value -= (lastItemOffset ?: 0)
        } else { // lastPosition.value < position.value
            currentOffset.value += itemOffset.value
        }
    }

    return currentOffset
}

