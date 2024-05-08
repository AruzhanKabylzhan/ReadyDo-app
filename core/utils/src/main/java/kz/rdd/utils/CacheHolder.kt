package kz.rdd.core.utils

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kz.rdd.core.utils.outcome.Outcome
import kz.rdd.core.utils.outcome.asSuccess
import kz.rdd.core.utils.outcome.doOnSuccess

class CacheHolder<T> {
    private val mutex = Mutex()
    private var cache = hashMapOf<String, T>()

    suspend fun withCached(
        key: String = "",
        block: suspend () -> Outcome<T>
    ): Outcome<T> = mutex.withLock {
        cache[key]?.asSuccess ?: block().doOnSuccess {
            cache[key] = it
        }
    }

    suspend fun setCache(
        key: String = "",
        value: T
    ) {
        mutex.withLock {
            cache[key] = value
        }
    }

    fun clean() {
        cache.clear()
    }
}
