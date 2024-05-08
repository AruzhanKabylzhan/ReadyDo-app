package kz.rdd.chats.presentation.forum_detail

import kz.rdd.chats.domain.ChatUseCase
import kz.rdd.chats.domain.ForumMessage
import kz.rdd.chats.presentation.forum.Forum
import kz.rdd.core.ui.base.effect.SuccessEffect
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.base.viewmodel.handleError
import kz.rdd.core.utils.outcome.doOnError
import kz.rdd.core.utils.outcome.doOnSuccess
import kz.rdd.store.UserStore

data class ForumDetailState(
    val forumList: List<Forum> = emptyList(),
    val forumMessages: List<ForumMessage> = emptyList(),
    val currentMessage: String = "",
) : UiState

class ForumDetailViewModel(
    private val id: Int,
    private val chatUseCase: ChatUseCase,
    private val userStore: UserStore,
) : BaseViewModel<ForumDetailState>() {
    override fun createInitialState() = ForumDetailState()

    init {
        load()
    }

    private fun load() {
        launch {
            chatUseCase.getForums()
                .doOnSuccess {
                    val forumList = it.map {
                        Forum(
                            id = it.id,
                            title = it.name,
                            userName = it.username,
                            imageUrl = it.photo,
                            description = it.description,
                            stars = it.grade.toDouble().toInt(),
                        )
                    }
                    val filtered = forumList.filter { item -> item.id == id }
                    setState { copy(forumList = filtered) }
                }
            chatUseCase.getForumMessages(currentState.forumList[0].id)
                .doOnSuccess {
                    setState { copy(forumMessages = it) }
                }
        }
    }

    fun onUpdateMessage(s: String) {
        setState { copy(currentMessage = s) }
    }

    fun onClickSend() {
        launch {
            chatUseCase.sendMessage(
                currentState.currentMessage,
                currentState.forumList[0].id,
                userStore.userDetails?.id ?: 1
            ).doOnSuccess {
                setEffect { SuccessEffect() }
                setState { copy(currentMessage = "") }
                load()
            }.doOnError {
                handleError(it)
            }
        }
    }
}