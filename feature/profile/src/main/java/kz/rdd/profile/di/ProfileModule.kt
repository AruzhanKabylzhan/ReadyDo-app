package kz.rdd.profile.di

import kz.rdd.core.network.RetrofitProvider
import kz.rdd.navigate.profile.ProfileContentDelegate
import kz.rdd.profile.data.ProfileRepoImpl
import kz.rdd.profile.data.network.ProfileApi
import kz.rdd.profile.domain.ProfileUseCase
import kz.rdd.profile.domain.repo.ProfileRepo
import kz.rdd.profile.presentation.ProfileContentDelegateImpl
import kz.rdd.profile.presentation.ProfileViewModel
import kz.rdd.profile.presentation.edit.ChangePasswordViewModel
import kz.rdd.profile.presentation.edit.EditProfileViewModel
import kz.rdd.profile.presentation.fav.FavViewModel
import kz.rdd.profile.presentation.order.OrderViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val profileModule = module {
    factory<ProfileContentDelegate> { ProfileContentDelegateImpl }

    single<ProfileApi> {
        get<RetrofitProvider>().baseRetrofit.create(ProfileApi::class.java)
    }

    singleOf(::ProfileRepoImpl) bind ProfileRepo::class

    factoryOf(::ProfileUseCase)

    viewModelOf(::ProfileViewModel)
    viewModelOf(::EditProfileViewModel)
    viewModelOf(::ChangePasswordViewModel)
    viewModelOf(::FavViewModel)
    viewModelOf(::OrderViewModel)
}