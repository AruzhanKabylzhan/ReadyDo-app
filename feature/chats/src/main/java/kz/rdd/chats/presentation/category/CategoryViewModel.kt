package kz.rdd.chats.presentation.category

import kz.rdd.core.ui.R
import kz.rdd.chats.presentation.chat_ai.ChatDestination
import kz.rdd.chats.presentation.forum.ForumDestination
import kz.rdd.core.ui.base.navigation.NavigateTo
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState

data class Category(
    val id: Int,
    val title: String,
    val icon: Int,
)
data class CategoriesState(
    val categories: List<Category> = emptyList(),
) : UiState

class CategoryViewModel : BaseViewModel<CategoriesState>() {
    override fun createInitialState() = CategoriesState(
        categories = listOf(
            Category(
                id = 1,
                title = "Chat With AI",
                icon = R.drawable.ic_chat_12
            ),
            Category(
                id = 2,
                title = "Forum",
                icon = R.drawable.ic_company_24
            )
        )
    )

    fun onClickCategory(id: Int){
        if(id == 1){
            navigate(NavigateTo(ChatDestination()))
        } else {
            navigate(NavigateTo(ForumDestination()))
        }
    }
}