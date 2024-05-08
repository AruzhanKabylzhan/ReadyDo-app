package kz.rdd.core.ui.screen.item_picker

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kz.rdd.core.ui.base.AcceptResult
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiEvent
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.base.viewmodel.handleError
import kz.rdd.core.ui.screen.item_picker.SheetPickerDestination.Companion.SHEET_PICKER_MULTIPLE_ACCEPT_KEY
import kz.rdd.core.ui.screen.item_picker.SheetPickerDestination.Companion.SHEET_PICKER_SINGLE_ACCEPT_KEY
import kz.rdd.core.ui.utils.VmRes
import kz.rdd.core.utils.outcome.doOnError
import kz.rdd.core.utils.outcome.doOnSuccess

@Immutable
interface SheetPickerActionItem {
    val itemName: VmRes<CharSequence>
}

class SimpleSheetPickerActionItem(
    val id: Int,
    val name: String,
) : SheetPickerActionItem {
    override val itemName = VmRes.Str(name)
}

data class SheetPickerItem(
    val item: SheetPickerActionItem,
    val isSelected: Boolean,
)

internal data class SheetPickerState(
    val title: VmRes<CharSequence>,
    val action: SheetPickerAction,
    val items: List<SheetPickerItem> = emptyList(),
    val isLoading: Boolean = false,
) : UiState

data class ReturnToPrevDestinationAndExitEvent(
    val data: AcceptResult.Data,
) : UiEvent

@Stable
internal class SheetPickerViewModel(
    private val launcher: SheetPickerLauncher,
) : BaseViewModel<SheetPickerState>() {
    override fun createInitialState() = SheetPickerState(
        title = launcher.title,
        action = launcher.sheetPickerAction,
    )

    init {
        load()
    }

    private fun load() {
        launch {
            setState { copy(isLoading = true) }
            launcher.behavior.loadItems()
                .doOnSuccess {
                    setState {
                        copy(
                            isLoading = false,
                            items = it
                        )
                    }
                }
                .doOnError {
                    handleError(it)
                    setState { copy(isLoading = false) }
                }
        }
    }

    fun onClickItem(item: SheetPickerItem) {
        if (currentState.action == SheetPickerAction.MULTIPLE) {
            setState {
                copy(items = items.map {
                    if (it == item) {
                        it.copy(isSelected = !it.isSelected)
                    } else {
                        it
                    }
                })
            }
            return
        }

        setEvent(
            ReturnToPrevDestinationAndExitEvent(
                AcceptResult.Data(
                    key = SHEET_PICKER_SINGLE_ACCEPT_KEY,
                    value = item.item,
                    additionalKey = launcher.additionalKey,
                )
            )
        )
    }

    fun onClickAction() {
        setEvent(
            ReturnToPrevDestinationAndExitEvent(
                AcceptResult.Data(
                    key = SHEET_PICKER_MULTIPLE_ACCEPT_KEY,
                    value = currentState.items.mapNotNull { item ->
                        item.item.takeIf { item.isSelected }
                    },
                    additionalKey = launcher.additionalKey,
                )
            )
        )
    }
}