package kz.rdd.core.user.di

import kz.rdd.core.network.RetrofitProvider
import kz.rdd.core.user.data.mapper.EmployeesListMapper
import kz.rdd.core.user.data.mapper.PersonMapper
import kz.rdd.core.user.data.mapper.UserProfileMapper
import kz.rdd.core.user.data.network.UserApi
import kz.rdd.core.user.data.repository.EmployeesListRepositoryImpl
import kz.rdd.core.user.data.repository.UserProfileRepositoryImpl
import kz.rdd.core.user.data.repository.UsersRepositoryImpl
import kz.rdd.core.user.domain.BusinessmanUserProfileHolder
import kz.rdd.core.user.domain.EmployeeUserProfileHolder
import kz.rdd.core.user.domain.EmployeesListUseCase
import kz.rdd.core.user.domain.GetCompanyUsersUseCase
import kz.rdd.core.user.domain.UserProfileUseCase
import kz.rdd.core.user.domain.repository.EmployeesListRepository
import kz.rdd.core.user.domain.repository.UserProfileRepository
import kz.rdd.core.user.domain.repository.UsersRepository
import kz.rdd.core.utils.AppConfig
import kz.rdd.store.UserSessionCleaner
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val userModule = module {

    single<UserApi> {
        get<RetrofitProvider>().baseRetrofit.create(UserApi::class.java)
    }

    singleOf(::UserProfileRepositoryImpl)
    single<UserProfileRepository> { get<UserProfileRepositoryImpl>() }
    singleOf(::EmployeesListRepositoryImpl) bind EmployeesListRepository::class bind UserSessionCleaner::class
    singleOf(::UsersRepositoryImpl) bind UsersRepository::class

    factoryOf(::UserProfileMapper)
    factoryOf(::EmployeesListMapper)
    factoryOf(::PersonMapper)

    factoryOf(::UserProfileUseCase)
    factoryOf(::EmployeesListUseCase)
    factoryOf(::GetCompanyUsersUseCase)

    singleOf(::EmployeeUserProfileHolder) bind UserSessionCleaner::class
    singleOf(::BusinessmanUserProfileHolder) bind UserSessionCleaner::class

    singleOf(::AppConfig)
}
