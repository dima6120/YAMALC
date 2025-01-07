package com.dima6120.search.ui

import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.ui.models.ErrorUIModel
import com.dima6120.ui.models.ItemUIModel
import com.dima6120.ui.models.ListItemUIModel
import com.dima6120.ui.models.TextUIModel

data class SearchScreenState(
    val query: String = "",
    val queries: List<String> = emptyList(),
    val searchResults: SearchResults = SearchResults.EmptyResults
)

sealed class SearchResults {

    data object EmptyResults: SearchResults()

    data class Results(val items: List<ListItemUIModel<AnimeItemUIModel>>): SearchResults()

    data object Loading: SearchResults()

    data class Error(val error: ErrorUIModel): SearchResults()
}

data class AnimeItemUIModel(
    val animeId: AnimeId,
    val title: TextUIModel,
    val picture: String?,
    val type: TextUIModel,
    val episodes: TextUIModel,
    val season: TextUIModel,
    val members: TextUIModel,
    val score: TextUIModel
): ItemUIModel {
    override val id: Any = animeId.id
}

