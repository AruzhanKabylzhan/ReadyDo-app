package kz.rdd.core.ui.screen

import android.content.Context
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import dev.olshevski.navigation.reimagined.pop
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.DialogDestination
import kz.rdd.core.ui.ext.openSettings
import kz.rdd.core.ui.ext.returnToPrevDestination
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.utils.VmRes
import kz.rdd.core.ui.utils.toStringCompose
import kz.rdd.core.ui.widgets.DialogAlert
import kz.rdd.core.ui.widgets.dialog.DialogWithTextField
import kz.rdd.core.utils.outcome.Outcome

@Parcelize
data class CommonDialogDestination(
    val title: VmRes.Parcelable<CharSequence>,
    val description: VmRes.Parcelable<CharSequence>,
    val positiveButtonText: VmRes.Parcelable<CharSequence> = VmRes.Str("OK"),
    val negativeButtonText: VmRes.Parcelable<CharSequence> = VmRes.StrRes(R.string.common_cancel),
    val behavior: Behavior = Behavior.None,
    val additionalKey: String? = null,
) : DialogDestination {

    companion object {
        const val RESULT_KEY = "CommonDialogDestination"
    }

    interface Behavior : Parcelable {
        fun onClickPositive(
            context: Context,
            controller: DestinationController,
        ) = Unit

        fun onClickNegative(
            context: Context,
            controller: DestinationController,
        ) = Unit

        @Parcelize
        object None : Behavior
    }

    @Composable
    override fun Content(controller: DestinationController) {
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current
        DialogAlert(
            title = title.toStringCompose(),
            description = description.toStringCompose(),
            action = positiveButtonText.toStringCompose(),
            cancel = negativeButtonText.toStringCompose(),
            actionPositiveColor = LocalAppTheme.colors.primaryText,
            actionNegativeColor = LocalAppTheme.colors.error,
            onActionNegative = {
                coroutineScope.launch {
                    behavior.onClickNegative(
                        context = context,
                        controller = controller,
                    )
                    controller.returnToPrevDestination(RESULT_KEY, value = false, additionalKey = additionalKey)
                    controller.dialogNavController.pop()
                }
            },
            onActionPositive = {
                coroutineScope.launch {
                    behavior.onClickPositive(
                        context = context,
                        controller = controller,
                    )
                    controller.returnToPrevDestination(RESULT_KEY, value = true, additionalKey = additionalKey)
                    controller.dialogNavController.pop()
                }
            }
        )
    }
}

@Parcelize
data class CommonTextFieldDialogDestination(
    val title: VmRes.Parcelable<CharSequence>,
    val description: VmRes.Parcelable<CharSequence>,
    val buttonText: VmRes.Parcelable<CharSequence>,
    val behavior: Behavior,
) : DialogDestination {

    override val dismissByTouch: Boolean
        get() = false
    @Composable
    override fun Content(controller: DestinationController) {
        DialogWithTextField(
            title = title,
            description = description,
            buttonText = buttonText,
            behavior = behavior,
        )
    }

    interface Behavior : Parcelable {
        val canBeEmpty: Boolean get() = false
        suspend fun onClickButton(fieldValue: String): Outcome<Unit>
    }
}

@Parcelize
object OpenSettingsDialogBehavior : CommonDialogDestination.Behavior {
    override fun onClickPositive(
        context: Context,
        controller: DestinationController
    ) {
        context.openSettings()
    }
}
