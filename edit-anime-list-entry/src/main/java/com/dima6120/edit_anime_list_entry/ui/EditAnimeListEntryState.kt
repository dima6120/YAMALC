package com.dima6120.edit_anime_list_entry.ui

import com.dima6120.core_api.model.mylist.ListStatusModel
import com.dima6120.ui.models.TextUIModel
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class EditAnimeListState(
    val availableListStatuses: List<ListStatusUIModel>,
    val episodes: Int?,
    val myListStatus: MyListStatusUIModel,
    val animeInfo: AnimeInfoUIModel,
    val actionButtonsEnabled: Boolean = true,
    val navigateBackEvent: StateEvent = consumed,
    val showSnackbarEvent: StateEventWithContent<TextUIModel> = consumed()
)

data class AnimeInfoUIModel(
    val title: TextUIModel,
    val status: TextUIModel,
    val episodes: TextUIModel
)

data class MyListStatusUIModel(
    val status: ListStatusModel,
    val score: Int,
    val episodesWatched: Int
)

data class ListStatusUIModel(
    val name: TextUIModel,
    val statusModel: ListStatusModel
)
