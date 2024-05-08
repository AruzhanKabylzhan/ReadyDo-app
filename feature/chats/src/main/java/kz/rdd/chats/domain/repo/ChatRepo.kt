package kz.rdd.chats.domain.repo

import kz.rdd.chats.domain.Forum
import kz.rdd.chats.domain.ForumMessage
import kz.rdd.core.utils.outcome.Outcome

interface ChatRepo {
    suspend fun getForums() : Outcome<List<Forum>>

    suspend fun getForumMessages(id: Int) : Outcome<List<ForumMessage>>

    suspend fun sendMessage(message: String, forum: Int, auther: Int) : Outcome<Unit>
}