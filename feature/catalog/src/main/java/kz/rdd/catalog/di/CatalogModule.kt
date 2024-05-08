package kz.rdd.catalog.di

import kz.rdd.catalog.data.CatalogRepoImpl
import kz.rdd.catalog.data.network.CatalogApi
import kz.rdd.catalog.domain.CatalogUseCase
import kz.rdd.catalog.domain.repo.CatalogRepo
import kz.rdd.catalog.presentation.CatalogContentDelegateImpl
import kz.rdd.catalog.presentation.CatalogViewModel
import kz.rdd.catalog.presentation.filter.FilterViewModel
import kz.rdd.catalog.presentation.meal_detail.MealDetailViewModel
import kz.rdd.core.network.RetrofitProvider
import kz.rdd.navigate.tours.ShopContentDelegate
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val catalogModule = module {
    factory<ShopContentDelegate> { CatalogContentDelegateImpl }

    single<CatalogApi> {
        get<RetrofitProvider>().baseRetrofit.create(CatalogApi::class.java)
    }

    singleOf(::CatalogRepoImpl) bind CatalogRepo::class

    factoryOf(::CatalogUseCase)

    viewModelOf(::CatalogViewModel)
    viewModelOf(::FilterViewModel)
    viewModelOf(::MealDetailViewModel)
}