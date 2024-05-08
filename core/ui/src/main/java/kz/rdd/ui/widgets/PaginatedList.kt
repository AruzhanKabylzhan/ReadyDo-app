@file:OptIn(ExperimentalFoundationApi::class)

package kz.rdd.core.ui.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.theme.LocalAppTheme

private const val LOADING_ITEM_KEY = "Paginated_LoadingIndicator"

@Composable
fun <T> PaginatedList(
    items: List<T>,
    isLoadingMore: Boolean,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
    key: ((item: T) -> Any)? = null,
    contentType: (T) -> Any? = { null },
    contentPadding: PaddingValues = PaddingValues(0.dp),
    listState: LazyListState = rememberLazyListState(),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    endScrollThresholdItems: Int = 2,
    itemContent: @Composable LazyItemScope.(T) -> Unit,
) {

    val isScrollToEnd by remember(items.size) {
        derivedStateOf {
            val lastItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastItemIndex >= items.size - endScrollThresholdItems
        }
    }

    val canLoad = isScrollToEnd && !isLoadingMore
    LaunchedEffect(canLoad) {
        if (canLoad) onLoadMore()
    }
    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement,
    ) {
        items(
            items = items,
            key = key,
            contentType = contentType,
            itemContent = itemContent,
        )

        if (isLoadingMore) {
            item(
                key = LOADING_ITEM_KEY,
                contentType = LOADING_ITEM_KEY,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .animateItemPlacement(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        color = LocalAppTheme.colors.primaryText
                    )
                }
            }
        }
    }
}
