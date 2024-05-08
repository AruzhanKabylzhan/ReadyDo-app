package kz.rdd.core.ui.screen.item_picker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.utils.VmRes
import kz.rdd.core.utils.outcome.Outcome

enum class SheetPickerAction {
    MULTIPLE, SINGLE,
}

interface SheetPickerBehavior : Parcelable {
    suspend fun loadItems(): Outcome<List<SheetPickerItem>>
}

@Parcelize
data class SheetPickerLauncher(
    val title: VmRes.Parcelable<CharSequence>,
    val sheetPickerAction: SheetPickerAction,
    val behavior: SheetPickerBehavior,
    val additionalKey: String? = null,
) : Parcelable
