package kz.rdd.catalog.presentation.filter

import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.store.UserStore

data class FilterState(
    val cuisineList: List<String> = emptyList(),
    val cuisine: String = "Italian",
    val tasteList: List<String> = emptyList(),
    val taste: String = "Spicy",
    val priceFrom: String = "",
    val priceTo: String = "",
) : UiState

class FilterViewModel(
    private val userStore: UserStore,
) : BaseViewModel<FilterState>() {
    override fun createInitialState() = FilterState(
        cuisineList = listOf(
            "Italian",
            "Japan",
            "Chinese",
            "Korean",
            "Russian",
            "Kazakh",
            "Georgian",
            "Turkish",
            "Mexican",
            "Uzbek",
            "Thai",
            "Indian",
            "American",
            "Caucasian",
        ),
        tasteList = listOf(
            "Spicy",
            "Salty",
            "Sweet",
            "Sour",
        )
    )

    fun onClickCuisine(title: String) {
        setState {
            copy(
                cuisine = title
            )
        }
    }

    fun onClickTaste(title: String) {
        setState {
            copy(
                taste = title
            )
        }
    }

    fun onUpdatePriceFrom(price: String) {
        setState {
            copy(
                priceFrom = price
            )
        }
    }

    fun onUpdatePriceTo(price: String) {
        setState {
            copy(
                priceTo = price
            )
        }
    }

    init {
        load()
    }

    private fun load() {
        if (userStore.cuisineIds != null) {
            setState { copy(cuisine = userStore.cuisineIds!!.firstOrNull()?.getCuisineName() ?: "Italian") }
        }
        if (userStore.tasteIds != null) {
            setState { copy(taste = userStore.tasteIds!!.firstOrNull()?.getTasteName() ?: "Spicy") }
        }
        if(userStore.startPrice != -1 && userStore.startPrice > 0) {
            setState { copy(priceFrom = userStore.startPrice.toString()) }
        }
        if(userStore.endPrice != -1 && userStore.endPrice > 0) {
            setState { copy(priceTo = userStore.endPrice.toString()) }
        }
    }

    fun onClickProceed() {
        userStore.cuisineIds = if(currentState.cuisine.isEmpty()) emptyList() else listOf(currentState.cuisine.getCuisineId())
        userStore.tasteIds = if(currentState.taste.isEmpty()) emptyList() else listOf(currentState.taste.getTasteId())
        userStore.endPrice =
            if (currentState.priceTo.isNotEmpty() && currentState.priceTo.isNotBlank()) currentState.priceTo.toInt() else -1
        userStore.startPrice =
            if (currentState.priceFrom.isNotEmpty() && currentState.priceFrom.isNotBlank()) currentState.priceFrom.toInt() else -1
        navigateBack()
    }

    fun onClickClear() {
        userStore.cuisineIds = emptyList()
        userStore.tasteIds = emptyList()
        userStore.startPrice = -1
        userStore.endPrice = -1
        setState {
            copy(
                cuisine = "",
                taste = "",
                priceFrom = "",
                priceTo = "",
            )
        }
    }

}

fun Int.getTasteName(): String {
    val taste = this
    return when (taste) {
        1 -> "Spicy"
        2 -> "Salty"
        3 -> "Sweet"
        4 -> "Sour"
        else -> "Spicy"
    }
}

fun Int.getCuisineName(): String {
    val cuisine = this
    return when (cuisine) {
        1 -> "Italian"
        2 -> "Japan"
        3 -> "Chinese"
        4 -> "Korean"
        5 -> "Russian"
        6 -> "Kazakh"
        7 -> "Georgian"
        8 -> "Turkish"
        9 -> "Mexican"
        10 -> "Uzbek"
        11 -> "Thai"
        12 -> "Indian"
        13 -> "American"
        14 -> "Caucasian"
        else -> "Italian"
    }
}

fun String.getTasteId(): Int {
    val taste = this
    return when (taste) {
        "Spicy" -> 1
        "Salty" -> 2
        "Sweet" -> 3
        "Sour" -> 4
        else -> 1
    }
}

fun String.getCuisineId(): Int {
    val cuisine = this
    return when (cuisine) {
        "Italian" -> 1
        "Japan" -> 2
        "Chinese" -> 3
        "Korean" -> 4
        "Russian" -> 5
        "Kazakh" -> 6
        "Georgian" -> 7
        "Turkish" -> 8
        "Mexican" -> 9
        "Uzbek" -> 10
        "Thai" -> 11
        "Indian" -> 12
        "American" -> 13
        "Caucasian" -> 14
        else -> 1
    }
}