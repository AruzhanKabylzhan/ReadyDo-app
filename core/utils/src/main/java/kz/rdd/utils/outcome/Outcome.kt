package kz.rdd.core.utils.outcome

sealed interface Outcome<out T> {

    data class Success<out T>(
        val data: T,
        val code: Int? = null
    ) : Outcome<T>

    data class SuccessNull<out T>(
        val code: Int? = null
    ) : Outcome<T>

    sealed interface Error : Outcome<Nothing> {

        data class ResponseError(
            val message: String?,
            val code: String?,
            val status: String? = null,
            val originalJson: String,
        ) : Error

        data object ConnectionError : Error

        data class UnknownError(
            val message: String? = null
        ) : Error
    }
}
