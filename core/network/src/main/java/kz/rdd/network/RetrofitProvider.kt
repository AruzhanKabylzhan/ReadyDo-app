package kz.rdd.core.network

import kz.rdd.core.utils.AppConfig
import okhttp3.OkHttpClient
import org.koin.core.component.KoinComponent
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

class RetrofitProvider(
    okHttpClient: OkHttpClient,
    appConfig: AppConfig,
    callAdapterFactory: CallAdapter.Factory,
    converterFactory: Converter.Factory,
) : KoinComponent {

    private val retrofitBuilder = Retrofit.Builder()
        .addCallAdapterFactory(callAdapterFactory)
        .addConverterFactory(converterFactory)
        .client(okHttpClient)

    val baseRetrofit: Retrofit by lazy {
        createRetrofit(appConfig.baseUrl)
    }

    fun createRetrofit(baseUrl: String, builder: Retrofit.Builder = retrofitBuilder): Retrofit {
        return builder.baseUrl(baseUrl).build()
    }
}