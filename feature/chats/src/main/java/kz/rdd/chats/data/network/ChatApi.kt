package kz.rdd.chats.data.network

import kz.rdd.chats.data.ForumDto
import kz.rdd.chats.data.ForumMessageDto
import kz.rdd.core.utils.outcome.Outcome
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatApi {
    @GET("forums/")
    suspend fun getForums() : Outcome<List<ForumDto>>

    @GET("forums/message/")
    suspend fun getForumMessage(
        @Query("forum_id") id: Int,
    ) : Outcome<List<ForumMessageDto>>

    @POST("forums/message/")
    suspend fun sendMessage(
        @Body request: RequestBody
    ) : Outcome<Unit>
}