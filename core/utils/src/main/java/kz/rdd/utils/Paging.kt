package kz.rdd.core.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.annotations.SerializedName
import kz.rdd.core.utils.outcome.Outcome

sealed class PagingSourceException : Exception() {
    data class ResponseError(
        override val message: String?,
    ) : PagingSourceException()

    object ConnectionError : PagingSourceException()

    data class UnknownError(
        val errorMessage: String? = null
    ) : PagingSourceException()
}

fun Outcome.Error.toPagingSourceException() = when (this) {
    Outcome.Error.ConnectionError -> PagingSourceException.ConnectionError
    is Outcome.Error.ResponseError -> PagingSourceException.ResponseError(message)
    is Outcome.Error.UnknownError -> PagingSourceException.UnknownError(message)
}

open class PagingResponse<T>(
    @SerializedName("results") val results: List<T>,
    @SerializedName("meta") val meta: Meta,
) {
    class Meta(
        @SerializedName("pagination") val pagination: Pagination,
    ) {
        class Pagination(
            @SerializedName("page") val page: Int,
            @SerializedName("pages") val pages: Int,
            @SerializedName("count") val count: Int,
        )
    }
}

fun <T, R> PagingResponse<T>.convert(
    map: (T) -> R
) = PagingResponse(
    meta = meta,
    results = results.map(map),
)

fun <T, R> PagingResponse<T>.convertNotNull(
    map: (T) -> R?
) = PagingResponse(
    meta = meta,
    results = results.mapNotNull(map),
)

fun <T, R> PagingResponse<T>.convertList(
    map: (List<T>) -> List<R>
) = PagingResponse(
    meta = meta,
    results = map(results),
)


abstract class PagingSourceHandler<T : Any>(
    private val limit: Int = LIMIT,
) : PagingSource<Int, T>() {

    abstract suspend fun load(limit: Int, page: Int): Outcome<PagingResponse<T>>

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return FIRST_PAGE
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val currentPage = params.key ?: FIRST_PAGE
        return when (val result = load(limit = limit, page = currentPage)) {
            is Outcome.Error -> {
                LoadResult.Error(result.toPagingSourceException())
            }
            is Outcome.Success -> {
                val list = result.data.results
                val pagination = result.data.meta.pagination
                val hasNext = pagination.page < pagination.pages
                LoadResult.Page(
                    data = list,
                    prevKey = (currentPage - 1).takeIf { it > FIRST_PAGE },
                    nextKey = if (list.isEmpty() || !hasNext) null else currentPage + 1
                )
            }
            else -> {
                LoadResult.Error(Outcome.Error.UnknownError().toPagingSourceException())
            }
        }
    }

    companion object {
        const val FIRST_PAGE = 1
        private const val LIMIT = 10
    }
}
