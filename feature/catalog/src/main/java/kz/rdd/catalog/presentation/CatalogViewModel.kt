package kz.rdd.catalog.presentation

import kz.rdd.catalog.domain.CatalogUseCase
import kz.rdd.catalog.domain.Food
import kz.rdd.catalog.presentation.filter.FilterDestination
import kz.rdd.catalog.presentation.meal_detail.MealDetailDestination
import kz.rdd.core.ui.base.navigation.NavigateTo
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.base.viewmodel.handleError
import kz.rdd.core.utils.outcome.doOnError
import kz.rdd.core.utils.outcome.doOnSuccess
import kz.rdd.store.UserStore

data class CatalogState(
    val meals: List<Food> = emptyList()
) : UiState

class CatalogViewModel(
    private val catalogUseCase: CatalogUseCase,
    private val userStore: UserStore,
) : BaseViewModel<CatalogState>() {
    override fun createInitialState() = CatalogState()

    init {
        load()
    }

    fun load() {
        launch {
            val startPrice = if(userStore.startPrice == -1) null else userStore.startPrice
            val endPrice = if(userStore.endPrice == -1) null else userStore.endPrice
            catalogUseCase.getFoods(
                userStore.cuisineIds,
                userStore.tasteIds,
                startPrice,
                endPrice,
                null
            ).doOnSuccess {
                setState {
                    copy(
                        meals = it
                    )
                }
            }.doOnError {
                handleError(it)
            }
        }
    }

    fun onClickFilters() {
        navigate(NavigateTo(FilterDestination()))
    }

    fun onClickMeal(food: Food) {
        navigate(NavigateTo(MealDetailDestination(food)))
    }
}