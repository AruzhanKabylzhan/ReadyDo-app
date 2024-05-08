package kz.rdd.core.ui.ext

import kz.rdd.core.ui.R
import kz.rdd.core.ui.utils.VmRes
import kz.rdd.core.utils.outcome.Outcome

fun Outcome.Error.getError() = when (this) {
    Outcome.Error.ConnectionError -> VmRes.StrRes(R.string.common_error_connection)
    is Outcome.Error.ResponseError -> this.message?.let {
        VmRes.Str(it)
    } ?: VmRes.StrRes(R.string.common_error_unknown)
    is Outcome.Error.UnknownError -> this.message?.let {
        VmRes.Str(it)
    } ?: VmRes.StrRes(R.string.common_error_unknown)
}