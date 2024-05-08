package kz.rdd.home.presentation

import kz.rdd.catalog.domain.CatalogUseCase
import kz.rdd.catalog.domain.Food
import kz.rdd.catalog.presentation.meal_detail.MealDetailDestination
import kz.rdd.core.ui.base.navigation.NavigateTo
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.base.viewmodel.handleError
import kz.rdd.core.utils.outcome.doOnError
import kz.rdd.core.utils.outcome.doOnSuccess
import kz.rdd.home.presentation.best.BestDestination
import kz.rdd.home.presentation.best.BestParams

data class HomeState(
    val meals: List<Food> = emptyList()
) : UiState

class HomeViewModel(
    private val catalogUseCase: CatalogUseCase,
) : BaseViewModel<HomeState>() {
    override fun createInitialState() = HomeState()

    init {
        load()
    }

    private fun load() {
        launch {
            catalogUseCase.getFoods(null, null, null, null, null)
                .doOnSuccess {
                    setState {
                        copy(
                            meals = if(it.size >= 6) it.subList(0, 6) else it
                        )
                    }
                }.doOnError {
                    handleError(it)
                }
        }
    }

    fun onClickBest(isChefs: Boolean) {
        if (isChefs) {
            navigate(NavigateTo(BestDestination(BestParams(isChefs))))
        } else {
            navigate(NavigateTo(BestDestination(BestParams(isChefs))))
        }
    }

    fun onCLickMeal(food: Food) {
        navigate(NavigateTo(MealDetailDestination(food)))
    }
}