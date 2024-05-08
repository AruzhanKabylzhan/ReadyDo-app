package kz.rdd.home.di

import kz.rdd.core.network.RetrofitProvider
import kz.rdd.home.data.HomeRepoImpl
import kz.rdd.home.data.network.HomeApi
import kz.rdd.home.domain.HomeUseCase
import kz.rdd.home.domain.repo.HomeRepo
import kz.rdd.home.presentation.HomeContentDelegateImpl
import kz.rdd.home.presentation.HomeViewModel
import kz.rdd.home.presentation.best.BestViewModel
import kz.rdd.navigate.home.HomeContentDelegate
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {
    factory<HomeContentDelegate> { HomeContentDelegateImpl }

    single<HomeApi> {
        get<RetrofitProvider>().baseRetrofit.create(HomeApi::class.java)
    }

    singleOf(::HomeRepoImpl) bind HomeRepo::class

    factoryOf(::HomeUseCase)

    viewModelOf(::HomeViewModel)
    viewModelOf(::BestViewModel)
}