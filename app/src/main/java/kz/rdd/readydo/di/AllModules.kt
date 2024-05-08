package kz.rdd.mamyq.di

import kz.rdd.MainActivityIntentReceiverImpl
import kz.rdd.busket.di.busketModule
import kz.rdd.catalog.di.catalogModule
import kz.rdd.chats.di.chatModule
import kz.rdd.core.local_storage.di.localStorageModule
import kz.rdd.core.network.di.networkModule
import kz.rdd.core.ui.di.uiModule
import kz.rdd.core.user.di.userModule
import kz.rdd.core.user.domain.BusinessmanUserProfileHolder
import kz.rdd.core.user.domain.EmployeeUserProfileHolder
import kz.rdd.core.user.domain.repository.EmployeesListRepository
import kz.rdd.forefront_nav.MainActivityIntentReceiver
import kz.rdd.home.di.homeModule
import kz.rdd.login.di.loginModule
import kz.rdd.main.start.startModule
import kz.rdd.navigate.di.navigateModule
import kz.rdd.profile.di.profileModule
import kz.rdd.store.UserLogoutCleanUseCase
import kz.rdd.store.di.storeModule
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val KoinApplication.allModules
    get() = modules(
        appModule,
        startModule,
        localStorageModule,
        networkModule,
        storeModule,
        uiModule,
        userModule,
        navigateModule,
        loginModule,
        homeModule,
        chatModule,
        catalogModule,
        busketModule,
        profileModule,
    )

val appModule = module {
    factoryOf(::MainActivityIntentReceiverImpl) bind MainActivityIntentReceiver::class

    factory {
        UserLogoutCleanUseCase(
            userStore = get(),
        ) {
            listOf(
                get<EmployeeUserProfileHolder>(),
                get<BusinessmanUserProfileHolder>(),
                get<EmployeesListRepository>()
            )
        }
    }
}
