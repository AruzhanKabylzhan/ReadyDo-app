package kz.rdd.chats.di

import kz.rdd.chats.data.ChatRepoImpl
import kz.rdd.chats.data.network.ChatApi
import kz.rdd.chats.domain.ChatUseCase
import kz.rdd.chats.domain.repo.ChatRepo
import kz.rdd.chats.presentation.category.CategoryContentDelegateImpl
import kz.rdd.chats.presentation.category.CategoryViewModel
import kz.rdd.chats.presentation.chat_ai.ChatViewModel
import kz.rdd.chats.presentation.forum.ForumViewModel
import kz.rdd.chats.presentation.forum_detail.ForumDetailViewModel
import kz.rdd.core.network.RetrofitProvider
import kz.rdd.navigate.manual.MapContentDelegate
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val chatModule = module {
    factory<MapContentDelegate> { CategoryContentDelegateImpl }

    single<ChatApi> {
        get<RetrofitProvider>().baseRetrofit.create(ChatApi::class.java)
    }

    singleOf(::ChatRepoImpl) bind ChatRepo::class

    factoryOf(::ChatUseCase)


    viewModelOf(::CategoryViewModel)
    viewModelOf(::ChatViewModel)
    viewModelOf(::ForumViewModel)
    viewModelOf(::ForumDetailViewModel)
}