package kz.rdd.login.presentation.otp.widgets

import androidx.compose.runtime.Composable
import dev.olshevski.navigation.reimagined.pop
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.base.DialogDestination
import kz.rdd.login.domain.model.VerifySmsTypes

@Parcelize
data class ChangePhoneNumberDialogDestination(
    val status: VerifySmsTypes,
) : DialogDestination {
    @Composable
    override fun Content(controller: DestinationController) {
        ChangePhoneNumberDialog(
            status = status,
            controller = controller,
            onActionPositive = {
                controller.navigateToMain()
                controller.dialogNavController.pop()
            }
        )
    }
}