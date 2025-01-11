package com.dima6120.edit_anime_list_entry.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dima6120.core_api.model.UseCaseResult
import com.dima6120.core_api.model.anime.AnimeBriefDetailsModel
import com.dima6120.core_api.model.mylist.AnimeListEntryUpdateModel
import com.dima6120.core_api.model.mylist.ListStatusModel
import com.dima6120.core_api.model.mylist.MyListStatusModel
import com.dima6120.core_api.ui.BaseViewModel
import com.dima6120.edit_anime_list_entry.usecase.DeleteAnimeListEntryUseCase
import com.dima6120.edit_anime_list_entry.usecase.UpdateAnimeListEntryUseCase
import com.dima6120.ui.models.TextUIModel
import com.dima6120.ui.models.orUnknownValue
import com.dima6120.ui.models.toTextUIModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditAnimeListEntryViewModel(
    private val animeBriefDetailsModel: AnimeBriefDetailsModel,
    private val myListStatusModel: MyListStatusModel,
    private val updateAnimeListEntryUseCase: UpdateAnimeListEntryUseCase,
    private val deleteAnimeListEntryUseCase: DeleteAnimeListEntryUseCase
): BaseViewModel<EditAnimeListState>() {

    private var animeListEntryUpdateModel = AnimeListEntryUpdateModel(
        animeId = animeBriefDetailsModel.id,
        status = myListStatusModel.status ?: ListStatusModel.PLAN_TO_WATCH,
        score = myListStatusModel.score,
        episodesWatched = myListStatusModel.episodesWatched
    )

    override val initialState: EditAnimeListState =
        EditAnimeListState(
            availableListStatuses = ListStatusModel.entries.map {
                ListStatusUIModel(
                    name = TextUIModel.Companion.from(it),
                    statusModel = it
                )
            },
            newEntry = animeBriefDetailsModel.myListStatus == null,
            episodes = animeBriefDetailsModel.episodes,
            myListStatus = animeListEntryUpdateModel.toMyListStatusUIModel(),
            animeInfo = animeBriefDetailsModel.toAnimeInfoUIModel()
        )

    fun statusSelected(listStatusModel: ListStatusModel) {
        if (listStatusModel !in animeBriefDetailsModel.status.acceptableListStatusModels) {
            return
        }

        updateAnimeListEntryUpdateModel {
            copy(
                status = listStatusModel
            )
        }

        updateMyListStatusUIModel()
    }

    fun episodesWatchedSelected(episodesWatched: Int) {
        updateAnimeListEntryUpdateModel {
            copy(
                episodesWatched = episodesWatched
            )
        }

        updateMyListStatusUIModel()
    }

    fun scoreSelected(score: Int) {
        updateAnimeListEntryUpdateModel {
            copy(
                score = score
            )
        }

        updateMyListStatusUIModel()
    }

    fun save() {
        updateState { copy(actionButtonsEnabled = false) }

        viewModelScope.launch {
            handleUpdateResult(
                result = updateAnimeListEntryUseCase(animeListEntryUpdateModel)
            )
        }
    }

    fun remove() {
        updateState { copy(actionButtonsEnabled = false) }

        viewModelScope.launch {
            handleUpdateResult(
                result = deleteAnimeListEntryUseCase(animeBriefDetailsModel.id)
            )
        }
    }

    fun navigateBackEventConsumed() {
        updateState {
            copy(navigateBackEvent = consumed)
        }
    }

    fun showSnackbarEventConsumed() {
        updateState {
            copy(showSnackbarEvent = consumed())
        }
    }

    private fun handleUpdateResult(result: UseCaseResult<Unit>) {
        when (result) {
            is UseCaseResult.Success ->
                updateState {
                    copy(navigateBackEvent = triggered)
                }

            is UseCaseResult.Error ->
                updateState {
                    copy(
                        actionButtonsEnabled = true,
                        showSnackbarEvent = triggered(result.error.toTextUIModel())
                    )
                }
        }
    }

    private fun updateMyListStatusUIModel() {
        updateState {
            copy(
                myListStatus = animeListEntryUpdateModel.toMyListStatusUIModel()
            )
        }
    }

    private inline fun updateAnimeListEntryUpdateModel(updater: AnimeListEntryUpdateModel.() -> AnimeListEntryUpdateModel) {
        animeListEntryUpdateModel = updater(animeListEntryUpdateModel)
    }

    private fun AnimeBriefDetailsModel.toAnimeInfoUIModel(): AnimeInfoUIModel =
        AnimeInfoUIModel(
            title = this.title.toTextUIModel(),
            status = TextUIModel.from(this.status),
            episodes = TextUIModel.stringResource(
                id = com.dima6120.ui.R.string.episodes,
                this.episodes?.toTextUIModel().orUnknownValue()
            )

        )

    private fun AnimeListEntryUpdateModel.toMyListStatusUIModel(): MyListStatusUIModel =
        MyListStatusUIModel(
            status = this.status,
            score = this.score,
            episodesWatched = this.episodesWatched
        )

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val animeBriefDetailsModel: AnimeBriefDetailsModel,
        private val myListStatusModel: MyListStatusModel,
        private val updateAnimeListEntryUseCase: UpdateAnimeListEntryUseCase,
        private val deleteAnimeListEntryUseCase: DeleteAnimeListEntryUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            EditAnimeListEntryViewModel(
                animeBriefDetailsModel = animeBriefDetailsModel,
                myListStatusModel = myListStatusModel,
                updateAnimeListEntryUseCase = updateAnimeListEntryUseCase,
                deleteAnimeListEntryUseCase = deleteAnimeListEntryUseCase
            ) as T
    }
}