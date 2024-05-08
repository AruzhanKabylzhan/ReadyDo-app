package kz.rdd.chats.domain

import kz.rdd.chats.domain.repo.ChatRepo
import kz.rdd.core.utils.outcome.Outcome

class ChatUseCase(
    private val repo: ChatRepo,
) {
    suspend fun getForums() = repo.getForums()

    suspend fun getForumMessages(id: Int) = repo.getForumMessages(id)

    suspend fun sendMessage(message: String, forum: Int, auther: Int) = repo.sendMessage(message, forum, auther)
}