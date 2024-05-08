package kz.rdd.core.network

import com.google.gson.Gson
import kz.rdd.core.utils.outcome.Outcome
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class CallDelegate<TIn, TOut>(protected val proxy: Call<TIn>) : Call<TOut> {
    override fun execute(): Response<TOut> = throw NotImplementedError()
    final override fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)
    final override fun clone(): Call<TOut> = cloneImpl()

    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled

    abstract fun enqueueImpl(callback: Callback<TOut>)
    abstract fun cloneImpl(): Call<TOut>
}

class CallAdapterFactory(
    private val gson: Gson
) : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit) =
        when (getRawType(returnType)) {
            Call::class.java -> {
                val callType = getParameterUpperBound(0, returnType as ParameterizedType)
                when (getRawType(callType)) {
                    Outcome::class.java -> {
                        val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                        OutcomeAdapter(resultType, gson)
                    }
                    else -> null
                }
            }
            else -> null
        }
}

class OutcomeCall<T>(proxy: Call<T>, private val gson: Gson) : CallDelegate<T, Outcome<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<Outcome<T>>) =
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val code = response.code()
                val body = response.body()
                val result: Outcome<T> = when (code) {
                    in 200 until 300 -> {
                        body?.let {
                            Outcome.Success(body, code)
                        }  ?: Outcome.SuccessNull(code)
                    }
                    else -> {
                        val errorBody = response.errorBody()?.string()
                        Timber.d("CallAdapter", "$errorBody - ${call.getUrl()}")
                        try {
                            val error = gson.fromJson(errorBody, ServerErrorResponse::class.java)
                            error.toOutcomeError(errorBody)
                        } catch (e: Exception) {
                            Outcome.Error.UnknownError()
                        }
                    }
                }
                callback.onResponse(this@OutcomeCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val result = when (t) {
                    is IOException -> Outcome.Error.ConnectionError
                    is HttpException -> {
                        val errorBody = t.response()?.errorBody()?.string()
                        try {
                            val error = gson.fromJson(errorBody, ServerErrorResponse::class.java)
                            error.toOutcomeError(errorBody)
                        } catch (e: Exception) {
                           Outcome.Error.UnknownError(t.message)
                        }
                    }
                    else -> Outcome.Error.UnknownError(t.message)
                }
                Timber.e(t)
                callback.onResponse(this@OutcomeCall, Response.success(result))
            }
        })

    override fun cloneImpl() = OutcomeCall(proxy.clone(), gson)

    override fun timeout(): Timeout {
        return Timeout()
    }

    private fun Call<T>.getUrl(): String {
        return (request() as Request).url.toString()
    }
}

private fun ServerErrorResponse.toOutcomeError(body: String?) = Outcome.Error.ResponseError(
    message = message,
    code = code,
    status = status,
    originalJson = body.orEmpty(),
)

class OutcomeAdapter(
    private val type: Type,
    private val gson: Gson
) : CallAdapter<Type, Call<Outcome<Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<Outcome<Type>> = OutcomeCall(call, gson)
}
