package kz.rdd.core.ui.screen.item_picker

import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.base.SheetDestination
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Parcelize
data class SheetPickerDestination(
    val launcher: SheetPickerLauncher,
    override val sendDefaultDismissedResult: Boolean = false,
) : SheetDestination {

    companion object {
        const val SHEET_PICKER_MULTIPLE_ACCEPT_KEY = "sheet_picker_multiple_accept_key"
        const val SHEET_PICKER_SINGLE_ACCEPT_KEY = "sheet_picker_single_accept_key"
    }

    @Composable
    override fun Content(controller: DestinationController) {
        val viewModel = getViewModel<SheetPickerViewModel> {
            parametersOf(launcher)
        }
        SheetPickerContainer(
            viewModel = viewModel,
        )
    }
}
