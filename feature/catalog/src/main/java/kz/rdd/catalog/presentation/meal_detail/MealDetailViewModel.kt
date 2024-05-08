package kz.rdd.catalog.presentation.meal_detail

import kz.rdd.busket.domain.BusketUseCase
import kz.rdd.catalog.domain.CatalogUseCase
import kz.rdd.catalog.domain.Food
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.effect.ToastEffect
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.base.viewmodel.handleError
import kz.rdd.core.ui.utils.VmRes
import kz.rdd.core.utils.outcome.doOnError
import kz.rdd.core.utils.outcome.doOnSuccess

data class MealDetailState(
    val title: String = "",
    val favorite: Boolean = false,
    val imageUrl: String = "",
    val userName: String = "",
    val stars: Int = 0,
    val price: Int = 15,
    val description: String = "",
    val id: Int = 0,
) : UiState
class MealDetailViewModel(
    private val food: Food,
    private val catalogUseCase: CatalogUseCase,
    private val busketUseCase: BusketUseCase,
) : BaseViewModel<MealDetailState>() {
    override fun createInitialState() = MealDetailState()

    init {
        load()
    }

    private fun load() {
        launch {
            setState {
                copy(
                    title = food.name,
                    favorite = false,
                    imageUrl = food.photo,
                    userName = food.username,
                    stars = if(food.grade.isNotEmpty()) food.grade.toDouble().toInt() else 0,
                    price = food.price,
                    description = food.ingredients,
                    id = food.id,
                )
            }
        }
    }

    fun onClickAddToFav(){
        launch {
            catalogUseCase.addToFav(food.id)
        }
    }

    fun onClickChat() {

    }

    fun onClickBusket(id: Int) {
        launch {
            busketUseCase.foodAdd(id, 1)
                .doOnSuccess {
                    setEffect { ToastEffect(VmRes.Str("Food added")) }
                }.doOnError {
                    handleError(it)
                }
        }
    }


}