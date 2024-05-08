package kz.rdd.core.ui.widgets.card_stack

sealed class SwipeSide {
    object START : SwipeSide()
    object TOP : SwipeSide()
    object END : SwipeSide()
    object BOTTOM : SwipeSide()
}
