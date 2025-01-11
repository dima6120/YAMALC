package com.dima6120.search.ui

import android.util.ArraySet
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dima6120.core_api.model.UseCaseResult
import com.dima6120.core_api.model.anime.AnimeBriefDetailsModel
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.ui.BaseViewModel
import com.dima6120.search.usecase.GetAnimeListUseCase
import com.dima6120.ui.models.ListItemUIModel
import com.dima6120.ui.models.TextUIModel
import com.dima6120.ui.models.orUnknownValue
import com.dima6120.ui.models.toErrorUIModel
import com.dima6120.ui.models.toTextUIModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchScreenViewModel(
    private val getAnimeListUseCase: GetAnimeListUseCase
): BaseViewModel<SearchScreenState>(){

    override val initialState = SearchScreenState()

    private var currentPage = 0
    private val existingAnimeIds = mutableSetOf<AnimeId>()
    private val queries = ArraySet<String>()
    private val items = mutableListOf<ListItemUIModel<AnimeItemUIModel>>()
    private var loadPageJob: Job? = null

    fun queryChanged(query: String) {
        updateState { copy(query = query) }
    }

    fun clearSearch() {
        resetSearch()

        updateState {
            copy(
                query = "",
                searchResults = SearchResults.EmptyResults
            )
        }
    }

    fun search(query: String) {
        if (query.isBlank()) {
            return
        }

        updateState { copy(query = query.trim()) }

        search()
    }

    fun search() {
        if (state.value.query.isBlank()) {
            return
        }

        resetSearch()

        queries.add(state.value.query.trim())

        updateState {
            copy(queries = this@SearchScreenViewModel.queries.reversed())
        }

        loadPage(currentPage)
    }

    fun nextPage() {
        if (loadPageJob != null) {
            return
        }

        Log.i(TAG, "nextPage(${currentPage + 1})")

        loadPage(currentPage + 1)
    }

    private fun resetSearch() {
        currentPage = 0

        items.clear()
        existingAnimeIds.clear()
    }

    private fun loadPage(page: Int) {
        if (page == 0) {
            updateState {
                copy(
                    searchResults = SearchResults.Loading
                )
            }
        } else {
            items.removeAll { it is ListItemUIModel.Error }
            items.add(ListItemUIModel.Loading)

            updateResults()
        }

        loadPageJob = viewModelScope.launch {
            val result = getAnimeListUseCase(
                query = state.value.query,
                page = page
            )

            when (result) {
                is UseCaseResult.Success -> {
                    items.removeAll { it is ListItemUIModel.Loading || it is ListItemUIModel.Error }

                    items.addAll(
                        result.value.mapNotNull {
                            if (!existingAnimeIds.contains(it.id)) {
                                existingAnimeIds.add(it.id)
                                ListItemUIModel.Item(it.toAnimeItemUIModel())
                            } else {
                                null
                            }
                        }
                    )

                    updateResults()

                    currentPage = page
                }

                is UseCaseResult.Error ->
                    if (page == 0) {
                        updateState {
                            copy(
                                searchResults = SearchResults.Error(result.error.toErrorUIModel())
                            )
                        }
                    } else {
                        items.remove(ListItemUIModel.Loading)
                        items.add(ListItemUIModel.Error(result.error.toTextUIModel()))

                        updateResults()
                    }
            }

            loadPageJob = null
        }
    }

    private fun updateResults() {
        updateState {
            copy(
                searchResults = SearchResults.Results(items.toList())
            )
        }
    }

    private fun AnimeBriefDetailsModel.toAnimeItemUIModel(): AnimeItemUIModel {
        val episodes = TextUIModel.stringResource(
            id = com.dima6120.ui.R.string.episodes,
            this.episodes?.toTextUIModel().orUnknownValue()
        )

        return AnimeItemUIModel(
            animeId = this.id,
            title = this.title.toTextUIModel(),
            picture = this.mainPicture?.medium,
            type = TextUIModel.from(this.type),
            episodes = episodes,
            season = TextUIModel.from(this.season),
            members = this.members.toTextUIModel(),
            score = this.score?.toTextUIModel().orUnknownValue()
        )
    }

    companion object {

        private val TAG = SearchScreenViewModel::class.simpleName
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getAnimeListUseCase: GetAnimeListUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SearchScreenViewModel(
                getAnimeListUseCase = getAnimeListUseCase
            ) as T
    }
}