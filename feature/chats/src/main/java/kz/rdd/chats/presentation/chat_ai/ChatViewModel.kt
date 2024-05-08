package kz.rdd.chats.presentation.chat_ai

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.chats.data.network.OpenAIApi
import kz.rdd.chats.data.network.OpenAIRequestBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class ChatState(
    val answer: String = "",
    val response: String = "",
) : UiState

class ChatViewModel : BaseViewModel<ChatState>() {
    override fun createInitialState() = ChatState()

    val messages = mutableStateListOf<Message>()

    object OpenAIManager {
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(
                Interceptor { chain ->
                    var request: Request? = null
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .addHeader("Authorization", "Bearer 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")
                    request = requestBuilder.build()
                    chain.proceed(request)
                })
            .build()


        private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val openAIApi: OpenAIApi = retrofit.create(OpenAIApi::class.java)
    }

    fun sendMessage(text: String, isUser: Boolean = true) {
        messages.add(Message(text, "user"))
        if (isUser) {
            viewModelScope.launch {
                withContext(Dispatchers.Main){
                    val response = OpenAIManager.openAIApi.generateResponse(OpenAIRequestBody(messages = messages))
                    messages.add(response.choices.first().message)
                }
            }
        }
    }

}

data class Message(val content: String, val role: String) {
    val isUser: Boolean
        get() = role == "user"
}




