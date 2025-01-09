package com.dima6120.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dima6120.ui.models.ItemUIModel
import com.dima6120.ui.models.ListItemUIModel

@Composable
fun <T: ItemUIModel> ItemList(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    items: List<ListItemUIModel<T>>,
    itemContent: @Composable (item: ListItemUIModel<T>) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
    ) {
        items(
            items = items,
            key = { it.id }
        ) {
            itemContent(it)
        }
    }
}