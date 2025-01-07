package com.dima6120.ui.models

interface ItemUIModel {
    val id: Any
}

sealed class ListItemUIModel<out T: ItemUIModel>(val id: Any) {

    data object Loading: ListItemUIModel<Nothing>(Int.MIN_VALUE)

    data class Error(val error: TextUIModel): ListItemUIModel<Nothing>(Int.MIN_VALUE + 1)

    data class Item<T: ItemUIModel>(val item: T): ListItemUIModel<T>(item.id)
}
