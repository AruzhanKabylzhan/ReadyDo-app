package kz.rdd.login.di

import kz.rdd.core.network.RetrofitProvider
import kz.rdd.core.network.TokenUseCase
import kz.rdd.login.data.network.AuthApi
import kz.rdd.login.data.repository.AuthRepositoryImpl
import kz.rdd.login.domain.AuthUseCase
import kz.rdd.login.domain.AuthUseCaseImpl
import kz.rdd.login.domain.repository.AuthRepository
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import kz.rdd.login.presentation.LoginViewModel
import kz.rdd.login.presentation.registration.RegistrationViewModel
import kz.rdd.login.presentation.forgot_password.ForgotPasswordViewModel
import kz.rdd.login.presentation.set_password.SetPasswordViewModel
import kz.rdd.login.presentation.otp.OtpViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named

val loginModule = module {

    single {
        get<RetrofitProvider>(named("NO_AUTH")).baseRetrofit.create(AuthApi::class.java)
    }

    singleOf(::AuthRepositoryImpl)
    single<AuthRepository> { get<AuthRepositoryImpl>() }

    factoryOf(::AuthUseCaseImpl)
    factory<AuthUseCase> { get<AuthUseCaseImpl>() }
    factory<TokenUseCase> { get<AuthUseCase>() }

    viewModelOf(::LoginViewModel)
    viewModelOf(::RegistrationViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::SetPasswordViewModel)
    viewModelOf(::OtpViewModel)
}