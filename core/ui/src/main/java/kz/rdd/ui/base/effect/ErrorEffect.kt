package kz.rdd.core.ui.base.effect

import android.content.Context
import android.widget.Toast
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.viewmodel.UiEffect
import kz.rdd.core.ui.utils.VmRes

open class ToastEffect(
    private val message: VmRes<CharSequence>,
): UiEffect {

    fun show(context: Context) {
        val message = message.get(context)
        if (message.isEmpty()) return
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}

class ErrorEffect(
    message: VmRes<CharSequence>,
): ToastEffect(message)

class SuccessEffect(
    message: VmRes<CharSequence>? = null,
): ToastEffect(message ?: VmRes.StrRes(R.string.common_success_request))
