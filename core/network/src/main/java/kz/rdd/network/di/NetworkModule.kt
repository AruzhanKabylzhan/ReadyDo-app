package kz.rdd.core.network.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.GsonBuilder
import kz.rdd.core.network.AuthInterceptor
import kz.rdd.core.network.CallAdapterFactory
import kz.rdd.core.network.LocalDateTimeTypeAdapter
import kz.rdd.core.network.LocalDateTypeAdapter
import kz.rdd.core.network.LocalTimeTypeAdapter
import kz.rdd.core.network.OffsetDateTimeTypeAdapter
import kz.rdd.core.network.RetrofitProvider
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateTypeAdapter().nullSafe())
            .registerTypeAdapter(LocalTime::class.java, LocalTimeTypeAdapter().nullSafe())
            .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeTypeAdapter().nullSafe())
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter().nullSafe())
            .create()
    }

    single<Converter.Factory> { GsonConverterFactory.create(get()) }

    single<CallAdapter.Factory> { CallAdapterFactory(gson = get()) }

    singleOf(::AuthInterceptor)

    single {
        ChuckerInterceptor.Builder(androidContext())
            .collector(
                ChuckerCollector(
                    context = androidContext(),
                    showNotification = true,
                    retentionPeriod = RetentionManager.Period.ONE_HOUR
                )
            )
            .maxContentLength(250_000L)
            .alwaysReadResponseBody(true)
            .build()
    }

    single {
        createOkHttpClient(
            get<ChuckerInterceptor>(),
            get<AuthInterceptor>(),
        )
    }

    single(named("NO_AUTH")) {
        createOkHttpClient(
            get<ChuckerInterceptor>(),
        )
    }
    single {
        RetrofitProvider(
            okHttpClient = get(),
            callAdapterFactory = get(),
            converterFactory = get(),
            appConfig = get()
        )
    }

    single(named("NO_AUTH")) {
        RetrofitProvider(
            okHttpClient = get(named("NO_AUTH")),
            callAdapterFactory = get(),
            converterFactory = get(),
            appConfig = get()
        )
    }
}

private fun createOkHttpClient(
    vararg interceptors: Interceptor
) = OkHttpClient.Builder()
    .connectTimeout(1, TimeUnit.MINUTES)
    .writeTimeout(1, TimeUnit.MINUTES)
    .readTimeout(1, TimeUnit.MINUTES)
    .run {
        var builder = this
        interceptors.forEach {
            builder = addInterceptor(it)
        }
        builder
    }
    .build()

