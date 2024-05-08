package kz.rdd.core.ui.widgets.card_stack

sealed class CardShadowSide {
    object ShadowStart : CardShadowSide()
    object ShadowEnd : CardShadowSide()
    object ShadowTop : CardShadowSide()
    object ShadowBottom : CardShadowSide()
}