package com.dima6120.anime_lists.ui

import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.model.mylist.ListStatusModel
import com.dima6120.edit_anime_list_entry_api.EditAnimeListEntryRoute
import com.dima6120.ui.models.ErrorUIModel
import com.dima6120.ui.models.ItemUIModel
import com.dima6120.ui.models.ListItemUIModel
import com.dima6120.ui.models.TextUIModel
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

sealed class AnimeListsState {

    data object Loading: AnimeListsState()

    data class Error(val error: ErrorUIModel): AnimeListsState()

    data class Authorized(
        val activeAnimeListIndex: Int = 0,
        val openEditAnimeListEntryScreenEvent: StateEventWithContent<EditAnimeListEntryRoute> = consumed(),
        val animeLists: List<AnimeListUIModel> = emptyList()
    ): AnimeListsState()

    data class Unauthorized(
        val openLinkEvent: StateEventWithContent<String> = consumed()
    ): AnimeListsState()
}

data class AnimeListUIModel(
    val name: TextUIModel,
    val status: ListStatusModel,
    val entries: List<ListItemUIModel<AnimeListEntryUIModel>> = listOf(ListItemUIModel.Loading)
)

data class AnimeListEntryUIModel(
    val animeId: AnimeId,
    val title: TextUIModel,
    val picture: String?,
    val type: TextUIModel,
    val season: TextUIModel,
    val status: TextUIModel,
    val episodes: TextUIModel,
    val episodesWatched: TextUIModel,
    val progress: Float,
): ItemUIModel {
    override val id: Any = animeId.id
}