package kz.rdd.busket.di

import kz.rdd.busket.data.mapper.BusketListMapper
import kz.rdd.busket.data.mapper.BusketProductsMapper
import kz.rdd.busket.data.network.BusketApi
import kz.rdd.busket.data.repo.BusketRepoImpl
import kz.rdd.busket.domain.BusketUseCase
import kz.rdd.busket.domain.repo.BusketRepo
import kz.rdd.busket.presentation.BusketContentDelegateImpl
import kz.rdd.busket.presentation.BusketViewModel
import kz.rdd.busket.presentation.pay.AddCardViewModel
import kz.rdd.busket.presentation.pay.PaySheetViewModel
import kz.rdd.busket.presentation.pay.PayViewModel
import kz.rdd.core.network.RetrofitProvider
import kz.rdd.navigate.navigate.TestContentDelegate
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val busketModule = module {
    factory<TestContentDelegate> { BusketContentDelegateImpl }

    single<BusketApi> {
        get<RetrofitProvider>().baseRetrofit.create(BusketApi::class.java)
    }

    singleOf(::BusketRepoImpl) bind BusketRepo::class

    factoryOf(::BusketProductsMapper)
    factoryOf(::BusketListMapper)

    factoryOf(::BusketUseCase)

    viewModelOf(::BusketViewModel)
    viewModelOf(::PayViewModel)
    viewModelOf(::PaySheetViewModel)
    viewModelOf(::AddCardViewModel)
}