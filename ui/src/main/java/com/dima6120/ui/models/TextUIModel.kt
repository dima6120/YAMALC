package com.dima6120.ui.models

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import com.dima6120.core_api.model.SeasonModel
import com.dima6120.core_api.model.anime.AnimeSeasonModel
import com.dima6120.core_api.model.anime.AnimeStatusModel
import com.dima6120.core_api.model.anime.AnimeTypeModel
import com.dima6120.core_api.model.mylist.ListStatusModel
import com.dima6120.ui.R

sealed class TextUIModel {

    data class StringResource(@StringRes val id: Int, val args: List<Any> = emptyList()): TextUIModel()

    data class ClearText(val text: String): TextUIModel()

    companion object {

        fun stringResource(
            @StringRes id: Int,
            vararg args: Any = emptyArray()
        ): StringResource = StringResource(id, args.toList())

        fun clearText(text: String): ClearText = ClearText(text)

        fun unknownValue(): TextUIModel = stringResource(R.string.unknown_value)

        fun from(animeTypeModel: AnimeTypeModel): TextUIModel {
            val id = when (animeTypeModel) {
                AnimeTypeModel.UNKNOWN -> R.string.type_unknown
                AnimeTypeModel.TV -> R.string.type_tv
                AnimeTypeModel.OVA -> R.string.type_ova
                AnimeTypeModel.MOVIE -> R.string.type_movie
                AnimeTypeModel.SPECIAL -> R.string.type_special
                AnimeTypeModel.ONA -> R.string.type_ona
                AnimeTypeModel.MUSIC -> R.string.type_music
                AnimeTypeModel.CM -> R.string.type_cm
            }

            return stringResource(id)
        }

        fun from(seasonModel: SeasonModel?): TextUIModel {
            seasonModel ?: return unknownValue()

            val id = when (seasonModel) {
                SeasonModel.WINTER -> R.string.winter_season
                SeasonModel.SPRING -> R.string.spring_season
                SeasonModel.SUMMER -> R.string.summer_season
                SeasonModel.FALL -> R.string.fall_season
            }

            return stringResource(id)
        }

        fun from(animeSeasonModel: AnimeSeasonModel?): TextUIModel {
            animeSeasonModel ?: return unknownValue()

            return stringResource(
                R.string.season,
                from(animeSeasonModel.season),
                animeSeasonModel.year.toString()
            )
        }

        fun from(listStatusModel: ListStatusModel): TextUIModel {
            val id = when (listStatusModel) {
                ListStatusModel.WATCHING -> R.string.watching
                ListStatusModel.COMPLETED -> R.string.completed
                ListStatusModel.ON_HOLD -> R.string.on_hold
                ListStatusModel.DROPPED -> R.string.dropped
                ListStatusModel.PLAN_TO_WATCH -> R.string.plan_to_watch
            }

            return stringResource(id)
        }

        fun from(animeStatusModel: AnimeStatusModel?): TextUIModel {
            animeStatusModel ?: return unknownValue()

            val id = when (animeStatusModel) {
                AnimeStatusModel.NOT_YET_AIRED -> com.dima6120.ui.R.string.status_not_yet_aired
                AnimeStatusModel.CURRENTLY_AIRING -> com.dima6120.ui.R.string.status_currently_airing
                AnimeStatusModel.FINISHED_AIRING -> com.dima6120.ui.R.string.status_finished_airing
            }

            return stringResource(id)
        }
    }
}

val TextUIModel.text: String
    @Composable
    @ReadOnlyComposable
    get() =
        when (this) {
            is TextUIModel.ClearText -> this.text
            is TextUIModel.StringResource -> {
                val args = this.args.map { arg ->
                    if (arg is TextUIModel) arg.text else arg
                }

                stringResource(id = this.id, *args.toTypedArray())
            }
        }

fun TextUIModel.text(context: Context): String =
    when (this) {
        is TextUIModel.ClearText -> this.text
        is TextUIModel.StringResource -> {
            val args = this.args.map { arg ->
                if (arg is TextUIModel) arg.text(context) else arg
            }

            context.getString(this.id, *args.toTypedArray())
        }
    }

fun TextUIModel?.orUnknownValue(): TextUIModel = this ?: TextUIModel.unknownValue()

fun Any.toTextUIModel(@StringRes id: Int): TextUIModel = TextUIModel.stringResource(id, this)

fun Any.toTextUIModel(): TextUIModel = TextUIModel.clearText(this.toString())
