package com.dima6120.anime_title.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dima6120.anime_title.R
import com.dima6120.anime_title.usecase.GetAnimeDetailsUseCase
import com.dima6120.core_api.model.RatingModel
import com.dima6120.core_api.model.SeasonModel
import com.dima6120.core_api.model.Time
import com.dima6120.core_api.model.UseCaseResult
import com.dima6120.core_api.model.anime.AnimeDetailsModel
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.model.anime.AnimeSeasonModel
import com.dima6120.core_api.model.anime.AnimeSourceModel
import com.dima6120.core_api.model.anime.AnimeStatusModel
import com.dima6120.core_api.model.anime.AnimeTypeModel
import com.dima6120.core_api.model.anime.RelatedAnimeModel
import com.dima6120.core_api.model.anime.RelationTypeModel
import com.dima6120.core_api.ui.BaseViewModel
import com.dima6120.core_api.utils.DateFormatter
import com.dima6120.ui.models.TextUIModel
import com.dima6120.ui.models.toErrorUIModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnimeTitleViewModel(
    private val animeId: AnimeId,
    private val getAnimeDetailsUseCase: GetAnimeDetailsUseCase,
    private val dateFormatter: DateFormatter
): BaseViewModel<AnimeTitleState>() {

    override val initialState = AnimeTitleState.Loading

    var commonEvents by mutableStateOf(AnimeTitleCommonEvents())
        private set

    private var animeDetails: AnimeDetailsModel? = null

    init {
        loadAnimeDetails()
    }

    fun loadAnimeDetails() {
        viewModelScope.launch {
            updateState { AnimeTitleState.Loading }

            when (val result = getAnimeDetailsUseCase(animeId)) {
                is UseCaseResult.Error ->
                    updateState { AnimeTitleState.Error(result.error.toErrorUIModel()) }

                is UseCaseResult.Success -> {
                    animeDetails = result.value

                    updateState {
                        AnimeTitleState.AnimeDetails(animeDetailsOrThrow().toAnimeDetailsUIModel())
                    }
                }
            }
        }
    }

    fun openAnimeInBrowser() {
        updateCommonEvents {
            copy(openLinkEvent = triggered("$ANIME_LINK_BASE${animeId.id}"))
        }
    }

    fun openLinkEventConsumed() {
        updateCommonEvents {
            copy(openLinkEvent = consumed())
        }
    }


    private inline fun updateCommonEvents(updater: AnimeTitleCommonEvents.() -> AnimeTitleCommonEvents) {
        commonEvents = updater(commonEvents)
    }

    private fun animeDetailsOrThrow(): AnimeDetailsModel = checkNotNull(animeDetails)

    private fun AnimeDetailsModel.toAnimeDetailsUIModel(): AnimeDetailsUIModel {
        val picture = listOfNotNull(
            this.mainPicture?.medium,
            *this.pictures.map { it.medium }.toTypedArray()
        )

        val titles = listOfNotNull(
            createAnimeTitleUIModel(R.string.english_name_field_title, this.englishTitle),
            this.japaneseTitle?.let {
                createAnimeTitleUIModel(R.string.japanese_name_field_title, it)
            }
        )

        val animeStats = AnimeStatsUIModel(
            score = this.score.toTextUIModel(),
            rank = this.rank.toTextUIModel(R.string.place),
            popularity = this.popularity.toTextUIModel(R.string.place),
            members = this.members.toTextUIModel()
        )

        val startDateString = this.startDate?.let(dateFormatter::formatDate)
        val endDateString = this.endDate?.let(dateFormatter::formatDate)
        val aired =
            if (startDateString != endDateString) {
                TextUIModel.stringResource(
                    id = R.string.aired,
                    startDateString.toTextUIModel(),
                    endDateString.toTextUIModel()
                )
            } else {
                startDateString.toTextUIModel()
            }

        val typeAndYear = TextUIModel.stringResource(
            R.string.anime_type_and_year,
            this.type.toTextUIModel(), this.season?.year.toTextUIModel()
        )

        val episodesDuration = this.episodeDuration
            ?.toLong()
            ?.let(dateFormatter::formatTime)
            .toTextUIModel()

        val episodesAndDuration = TextUIModel.stringResource(
            R.string.episodes_and_episode_duration,
            this.episodes.toTextUIModel(),
            episodesDuration
        )

        val animeInfo = AnimeInfoUIModel(
            season = this.season.toTextUIModel(),
            status = this.status.toTextUIModel(),
            aired = aired,
            synopsys = this.synopsis.toTextUIModel(),
            typeAndYear = typeAndYear,
            episodesAndDuration = episodesAndDuration,
            rating = this.rating.toTextUIModel(),
            source = this.source.toTextUIModel(),
            genres = this.genres.map { it.name.toTextUIModel() },
            studios = this.studios.joinToString(separator = ", ") { it.name }.toTextUIModel()
        )

        val relatedAnimeItems = this.relatedAnime
            .groupBy { it.relationType }
            .map { entry ->
                entry.key to entry.value.map { it.toAnimeItemUIModel() }
            }
            .sortedBy { it.first }
            .map {
                RelatedAnimeItemUIModel(
                    relationType = it.first.toTextUIModel(),
                    animeItems = it.second
                )
            }

        return AnimeDetailsUIModel(
            pictures = picture,
            titles = titles,
            animeStats = animeStats,
            animeInfo = animeInfo,
            relatedAnimeItems = relatedAnimeItems
        )
    }

    private fun RelationTypeModel.toTextUIModel(): TextUIModel {
        val id = when (this) {
            RelationTypeModel.SEQUEL -> R.string.relation_type_sequel
            RelationTypeModel.PREQUEL -> R.string.relation_type_prequel
            RelationTypeModel.ALTERNATIVE_SETTING -> R.string.relation_type_alternative_setting
            RelationTypeModel.ALTERNATIVE_VERSION -> R.string.relation_type_alternative_version
            RelationTypeModel.SIDE_STORY -> R.string.relation_type_side_story
            RelationTypeModel.PARENT_STORY -> R.string.relation_type_parent_story
            RelationTypeModel.SUMMARY -> R.string.relation_type_summary
            RelationTypeModel.FULL_STORY -> R.string.relation_type_full_story
            RelationTypeModel.OTHER -> R.string.relation_type_other
            RelationTypeModel.CHARACTER -> R.string.relation_type_character
        }

        return TextUIModel.stringResource(id)
    }


    private fun RelatedAnimeModel.toAnimeItemUIModel(): AnimeItemUIModel =
        AnimeItemUIModel(
            id = this.id,
            title = this.title.toTextUIModel()
        )

    private fun Time?.toTextUIModel(): TextUIModel =
        this?.let {
            when (it) {
                is Time.Hours -> TextUIModel.stringResource(R.string.duration_hours, it.hours)

                is Time.HoursAndMinutes ->
                    TextUIModel.stringResource(
                        R.string.duration_hours_and_minutes,
                        it.hours, it.minutes
                    )

                is Time.Minutes -> TextUIModel.stringResource(R.string.duration_minutes, it.minutes)

                is Time.Seconds -> TextUIModel.stringResource(R.string.duration_seconds, it.seconds)
            }
        }.orUnknownValue()

    private fun AnimeSeasonModel?.toTextUIModel(): TextUIModel =
        this?.let {
            TextUIModel.stringResource(
                R.string.season,
                it.season.toTextUIModel(),
                it.year.toString()
            )
        }.orUnknownValue()

    private fun AnimeSourceModel?.toTextUIModel(): TextUIModel =
        this?.let {
            val id = when (this) {
                AnimeSourceModel.OTHER -> R.string.source_other
                AnimeSourceModel.ORIGINAL -> R.string.source_original
                AnimeSourceModel.MANGA -> R.string.source_manga
                AnimeSourceModel.FOUR_KOMA_MANGA -> R.string.source_4_koma_manga
                AnimeSourceModel.WEB_MANGA -> R.string.source_web_manga
                AnimeSourceModel.DIGITAL_MANGA -> R.string.source_digital_manga
                AnimeSourceModel.NOVEL -> R.string.source_novel
                AnimeSourceModel.LIGHT_NOVEL -> R.string.source_light_novel
                AnimeSourceModel.VISUAL_NOVEL -> R.string.source_visual_novel
                AnimeSourceModel.GAME -> R.string.source_game
                AnimeSourceModel.CARD_GAME -> R.string.source_card_game
                AnimeSourceModel.BOOK -> R.string.source_book
                AnimeSourceModel.PICTURE_BOOK -> R.string.source_picture_book
                AnimeSourceModel.RADIO -> R.string.source_radio_book
                AnimeSourceModel.MUSIC -> R.string.source_music
            }

            TextUIModel.stringResource(id)
        }.orUnknownValue()

    private fun RatingModel?.toTextUIModel(): TextUIModel =
        this?.let {
            val id = when (this) {
                RatingModel.G -> R.string.rating_g
                RatingModel.PG -> R.string.rating_pg
                RatingModel.PG_13 -> R.string.rating_pg_13
                RatingModel.R -> R.string.rating_r
                RatingModel.R_PLUS -> R.string.rating_r_plus
                RatingModel.RX -> R.string.rating_rx
            }

            TextUIModel.stringResource(id)
        }.orUnknownValue()

    private fun AnimeTypeModel.toTextUIModel(): TextUIModel {
        val id = when (this) {
            AnimeTypeModel.UNKNOWN -> R.string.type_unknown
            AnimeTypeModel.TV -> R.string.type_tv
            AnimeTypeModel.OVA -> R.string.type_ova
            AnimeTypeModel.MOVIE -> R.string.type_movie
            AnimeTypeModel.SPECIAL -> R.string.type_special
            AnimeTypeModel.ONA -> R.string.type_ona
            AnimeTypeModel.MUSIC -> R.string.type_music
            AnimeTypeModel.CM -> R.string.type_cm
        }

        return TextUIModel.stringResource(id)
    }

    private fun AnimeStatusModel?.toTextUIModel(): TextUIModel =
        this?.let {
            val id = when (it) {
                AnimeStatusModel.NOT_YET_AIRED -> R.string.status_not_yet_aired
                AnimeStatusModel.CURRENTLY_AIRING -> R.string.status_currently_airing
                AnimeStatusModel.FINISHED_AIRING -> R.string.status_finished_airing
            }

            TextUIModel.stringResource(id)
        }.orUnknownValue()

    private fun SeasonModel?.toTextUIModel(): TextUIModel =
        this?.let {
            val id = when (it) {
                SeasonModel.WINTER -> R.string.winter_season
                SeasonModel.SPRING -> R.string.spring_season
                SeasonModel.SUMMER -> R.string.summer_season
                SeasonModel.FALL -> R.string.fall_season
            }

            TextUIModel.stringResource(id)
        }.orUnknownValue()

    private fun Any?.toTextUIModel(@StringRes id: Int): TextUIModel =
        this?.let { TextUIModel.stringResource(id, it) }.orUnknownValue()

    private fun Any?.toTextUIModel(): TextUIModel =
        this?.let { TextUIModel.clearText(it.toString()) }.orUnknownValue()

    private fun TextUIModel?.orUnknownValue(): TextUIModel =
        this ?: TextUIModel.stringResource(R.string.unknown_value)

    private fun createAnimeTitleUIModel(@StringRes language: Int, name: String): AnimeTitleUIModel =
        AnimeTitleUIModel(
            language = TextUIModel.stringResource(language),
            name = TextUIModel.clearText(name)
        )

    companion object {

        private const val ANIME_LINK_BASE = "https://myanimelist.net/anime/"
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val animeId: Int,
        private val getAnimeDetailsUseCase: GetAnimeDetailsUseCase,
        private val dateFormatter: DateFormatter
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
             AnimeTitleViewModel(
                 animeId = AnimeId(animeId),
                 getAnimeDetailsUseCase = getAnimeDetailsUseCase,
                 dateFormatter = dateFormatter
             ) as T
    }
}