package kz.rdd.core.utils.outcome

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import java.lang.Exception

inline fun <T> withTry(block: () -> Outcome<T>): Outcome<T> {
    return try {
        block()
    } catch (e: Exception) {
        Timber.e(e)
        Outcome.Error.ResponseError(
            message = null,
            code = null,
            originalJson = "",
        )
    }
}

inline fun <T, R> Outcome<T>.map(
    transform: (T) -> R
): Outcome<R> {
    return withTry {
        when (this) {
            is Outcome.Success -> Outcome.Success(
                data = transform(data),
                code = code,
            )
            is Outcome.SuccessNull -> Outcome.SuccessNull(code)
            is Outcome.Error.ResponseError -> clone()
            Outcome.Error.ConnectionError -> Outcome.Error.ConnectionError
            is Outcome.Error.UnknownError -> Outcome.Error.UnknownError(message)
        }
    }
}

inline fun <T, R> Outcome<T>.mapValue(
    transform: () -> R
): Outcome<R> {
    return withTry {
        when (this) {
            is Outcome.Success -> Outcome.Success(
                data = transform(),
                code = code,
            )
            is Outcome.SuccessNull -> Outcome.Success(
                data = transform(),
                code = code,
            )
            is Outcome.Error.ResponseError -> clone()
            Outcome.Error.ConnectionError -> Outcome.Error.ConnectionError
            is Outcome.Error.UnknownError -> Outcome.Error.UnknownError(message)
        }
    }
}

suspend inline fun <T, R> Outcome<T>.transform(
    crossinline transform: suspend (T) -> Outcome<R>
): Outcome<R> {
    return withTry {
        when (this) {
            is Outcome.Success -> transform(this.data)
            is Outcome.SuccessNull -> Outcome.SuccessNull(code)
            is Outcome.Error.ResponseError -> clone()
            Outcome.Error.ConnectionError -> Outcome.Error.ConnectionError
            is Outcome.Error.UnknownError -> Outcome.Error.UnknownError(message)
        }
    }
}

fun Outcome.Error.ResponseError.clone() = Outcome.Error.ResponseError(
    message = message,
    code = code,
    status = status,
    originalJson = originalJson,
)

val <T> Outcome<T>.successOrNull get() = (this as? Outcome.Success)?.data

val <T> T.asSuccess get() = Outcome.Success(this)

fun <T> Outcome<T>.onSuccess(
    action: (data: T) -> Unit
): Outcome<T> {
    if (this is Outcome.Success && data != null) action.invoke(this.data)
    return this
}

fun <T> Outcome<T>.onError(
    action: (error: Outcome.Error) -> Unit
): Outcome<T> {
    if (this is Outcome.Error) action.invoke(this)
    return this
}

fun <T> Outcome<T>.onResponseError(
    action: (error: Outcome.Error.ResponseError) -> Unit
): Outcome<T> {
    if (this is Outcome.Error.ResponseError) action.invoke(this)
    return this
}

fun <T> Outcome<T>.onConnectionError(
    action: () -> Unit
): Outcome<T> {
    if (this is Outcome.Error.ConnectionError) action.invoke()
    return this
}

fun <T> Outcome<T>.onUnknownError(
    action: () -> Unit
): Outcome<T> {
    if (this is Outcome.Error.UnknownError) action.invoke()
    return this
}

fun <T> Outcome<T>.onComplete(
    action: (Outcome<T>) -> Unit
): Outcome<T> {
    action(this)
    return this
}

fun <T> Outcome<T>.updateStateFlow(
    flow: MutableStateFlow<Outcome<T>>
): Outcome<T> {
    flow.value = this
    return this
}

fun <T> Outcome<T>.updateSharedFlow(
    flow: MutableSharedFlow<Outcome<T>>
): Outcome<T> {
    if (this is Outcome.Success) flow.tryEmit(this)
    return this
}

suspend fun <T> Outcome<T>.doOnSuccess(
    action: suspend (data: T) -> Unit
): Outcome<T> {
    if (this is Outcome.Success<T>) action.invoke(data)
    return this
}

suspend fun <T> Outcome<T>.doOnSuccessNullable(
    action: suspend () -> Unit
): Outcome<T> {
    if (this is Outcome.SuccessNull<T>) action.invoke()
    return this
}

suspend fun <T> Outcome<T>.doOnError(
    action: suspend (error: Outcome.Error) -> Unit
): Outcome<T> {
    if (this is Outcome.Error) action.invoke(this)
    return this
}

suspend fun <T> Outcome<T>.doOnResponseError(
    action: suspend (error: Outcome.Error.ResponseError) -> Unit
): Outcome<T> {
    if (this is Outcome.Error.ResponseError) action.invoke(this)
    return this
}

suspend fun <T> Outcome<T>.doOnConnectionError(
    action: suspend () -> Unit
): Outcome<T> {
    if (this is Outcome.Error.ConnectionError) action.invoke()
    return this
}

suspend fun <T> Outcome<T>.doOnUnknownError(
    action: suspend (message: String?) -> Unit
): Outcome<T> {
    if (this is Outcome.Error.UnknownError) action.invoke(message)
    return this
}
