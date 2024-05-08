package kz.rdd.core.ui.base.effect

import android.content.Context
import kz.rdd.core.ui.base.viewmodel.UiEffect

fun baseEffectHandler(
    effect: UiEffect?,
    context: Context,
    handle: (UiEffect.() -> Unit)? = null
) {
    when(effect) {
        is ToastEffect -> effect.show(context)
        else -> effect?.let { handle?.invoke(it) }
    }
}
