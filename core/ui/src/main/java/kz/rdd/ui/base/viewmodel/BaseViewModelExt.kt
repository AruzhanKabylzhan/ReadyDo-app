package kz.rdd.core.ui.base.viewmodel

import androidx.annotation.StringRes
import kz.rdd.core.ui.base.effect.ErrorEffect
import kz.rdd.core.ui.ext.getError
import kz.rdd.core.ui.utils.VmRes
import kz.rdd.core.utils.outcome.Outcome

fun BaseViewModel<*>.handleError(error: Outcome.Error) {
    setEffect {
        ErrorEffect(error.getError())
    }
}

fun BaseViewModel<*>.sendErrorMessage(error: String) {
    setEffect {
        ErrorEffect(VmRes.Str(error))
    }
}

fun BaseViewModel<*>.sendErrorMessage(@StringRes errorRes: Int) {
    setEffect {
        ErrorEffect(VmRes.StrRes(errorRes))
    }
}
