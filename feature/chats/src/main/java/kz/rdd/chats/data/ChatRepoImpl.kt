package kz.rdd.chats.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.rdd.chats.data.network.ChatApi
import kz.rdd.chats.domain.Forum
import kz.rdd.chats.domain.ForumMessage
import kz.rdd.chats.domain.repo.ChatRepo
import kz.rdd.core.utils.ext.addFormDataPartWithSkippingNull
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.core.utils.outcome.map
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ChatRepoImpl(
    private val api: ChatApi
) : ChatRepo {
    override suspend fun getForums(): Outcome<List<Forum>> = withContext(Dispatchers.IO) {
        api.getForums().map {
            it.map {
                Forum(
                    id = it.id,
                    name = it.name,
                    photo = it.photo.orEmpty(),
                    description = it.description,
                    username = it.username,
                    grade = it.grade
                )
            }
        }
    }

    override suspend fun getForumMessages(id: Int): Outcome<List<ForumMessage>> = withContext(Dispatchers.IO) {
        api.getForumMessage(id).map {
            it.map {
                ForumMessage(
                    id = it.id,
                    createdAt = it.createdAt,
                    message = it.message.orEmpty(),
                    forum = it.forum,
                    username = it.username.orEmpty(),
                )
            }
        }
    }

    override suspend fun sendMessage(message: String, forum: Int, auther: Int): Outcome<Unit> = withContext(Dispatchers.IO) {
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPartWithSkippingNull("message", message)
            .addFormDataPartWithSkippingNull("forum", forum.toString())
            .addFormDataPartWithSkippingNull("auther", auther.toString())
            .build()
        api.sendMessage(requestBody)
    }
}