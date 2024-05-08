package kz.rdd.chats.presentation.forum

import kz.rdd.chats.domain.ChatUseCase
import kz.rdd.chats.presentation.forum_detail.ForumDetailDestination
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.navigation.NavigateTo
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.utils.outcome.doOnSuccess
import java.time.LocalDate

data class Forum(
    val id: Int,
    val title: String,
    val userName: String,
    val description: String,
    val imageUrl: String,
    val stars: Int,
)
data class ForumState(
    val forumList: List<Forum> = emptyList()
) : UiState
class ForumViewModel(
    private val chatUseCase: ChatUseCase,
) : BaseViewModel<ForumState>() {
    override fun createInitialState() = ForumState()

    init {
        load()
    }

    private fun load() {
        launch {
            chatUseCase.getForums()
                .doOnSuccess {
                    setState {
                        copy(
                            forumList = it.map {
                                Forum(
                                    id = it.id,
                                    title = it.name,
                                    userName = it.username,
                                    imageUrl = it.photo,
                                    description = it.description,
                                    stars = it.grade.toDouble().toInt(),
                                )
                            }
                        )
                    }
                }
        }
    }

    fun onClickForum(id: Int){
        navigate(NavigateTo(ForumDetailDestination(id)))
    }
}