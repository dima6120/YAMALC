package com.dima6120.anime_title.ui

import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.model.mylist.ListStatusModel
import com.dima6120.edit_anime_list_entry_api.EditAnimeListEntryRoute
import com.dima6120.ui.models.ErrorUIModel
import com.dima6120.ui.models.TextUIModel
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered

sealed class AnimeTitleState {

    data object Loading: AnimeTitleState()

    data class Error(val error: ErrorUIModel): AnimeTitleState()

    data class AnimeDetails(
        val listStatusModel: ListStatusModel?,
        val animeDetails: AnimeDetailsUIModel,
        val openEditAnimeListEntryScreenEvent: StateEventWithContent<EditAnimeListEntryRoute> = consumed()
    ) : AnimeTitleState()
}

data class AnimeTitleCommonEvents(
    val openLinkEvent: StateEventWithContent<String> = consumed()
)

data class AnimeDetailsUIModel(
    val pictures: List<String>,
    val titles: List<AnimeTitleUIModel>,
    val animeStats: AnimeStatsUIModel,
    val animeInfo: AnimeInfoUIModel,
    val relatedAnimeItems: List<RelatedAnimeItemUIModel>
)

data class RelatedAnimeItemUIModel(
    val relationType: TextUIModel,
    val animeItems: List<AnimeItemUIModel>
)

data class AnimeItemUIModel(
    val id: AnimeId,
    val title: TextUIModel
)

data class AnimeInfoUIModel(
    val season: TextUIModel,
    val status: TextUIModel,
    val aired: TextUIModel,
    val synopsys: TextUIModel,
    val typeAndYear: TextUIModel,
    val episodesAndDuration: TextUIModel,
    val rating: TextUIModel,
    val source: TextUIModel,
    val genres: List<TextUIModel>,
    val studios: TextUIModel
)

data class AnimeStatsUIModel(
    val score: TextUIModel,
    val rank: TextUIModel,
    val popularity: TextUIModel,
    val members: TextUIModel,
)

data class AnimeTitleUIModel(
    val language: TextUIModel,
    val name: TextUIModel
)
