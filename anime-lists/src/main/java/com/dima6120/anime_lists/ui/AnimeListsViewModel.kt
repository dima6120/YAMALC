package com.dima6120.anime_lists.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dima6120.anime_lists.R
import com.dima6120.anime_lists.usecase.GetAnimeStatisticsUseCase
import com.dima6120.anime_lists.usecase.GetMyAnimeListUseCase
import com.dima6120.core_api.model.UseCaseResult
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.model.mylist.AnimeListEntryUpdateModel
import com.dima6120.core_api.model.mylist.ListStatusModel
import com.dima6120.core_api.model.mylist.MyListAnimeModel
import com.dima6120.core_api.ui.BaseViewModel
import com.dima6120.core_api.usecase.GetAuthorizeLinkUseCase
import com.dima6120.core_api.usecase.GetDeletedAnimeListEntryFlowUseCase
import com.dima6120.core_api.usecase.GetLoggedInFlowUseCase
import com.dima6120.core_api.usecase.GetUpdatedAnimeListEntryFlowUseCase
import com.dima6120.edit_anime_list_entry_api.EditAnimeListEntryRoute
import com.dima6120.ui.models.ListItemUIModel
import com.dima6120.ui.models.TextUIModel
import com.dima6120.ui.models.orUnknownValue
import com.dima6120.ui.models.toErrorUIModel
import com.dima6120.ui.models.toTextUIModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnimeListsViewModel(
    private val getAuthorizeLinkUseCase: GetAuthorizeLinkUseCase,
    private val getLoggedInFlowUseCase: GetLoggedInFlowUseCase,
    private val getAnimeStatisticsUseCase: GetAnimeStatisticsUseCase,
    private val getMyAnimeListUseCase: GetMyAnimeListUseCase,
    private val getUpdatedAnimeListEntryFlowUseCase: GetUpdatedAnimeListEntryFlowUseCase,
    private val getDeletedAnimeListEntryFlowUseCase: GetDeletedAnimeListEntryFlowUseCase
): BaseViewModel<AnimeListsState>() {

    override val initialState: AnimeListsState = AnimeListsState.Loading

    private val animeLists = ListStatusModel.entries.map { AnimeListInternal(status = it) }
    private var loadPageJobs = ListStatusModel.entries.map { null }.toTypedArray<Job?>()

    init {
        viewModelScope.launch {
            getLoggedInFlowUseCase()
                .collect { loggedIn ->
                    if (loggedIn) {
                        initialAnimeListLoad()
                    } else {
                        updateState { AnimeListsState.Unauthorized() }
                    }
                }
        }

        viewModelScope.launch {
            getUpdatedAnimeListEntryFlowUseCase().collect(::updateLists)
        }

        viewModelScope.launch {
            getDeletedAnimeListEntryFlowUseCase().collect(::deleteAnimeListEntry)
        }
    }

    fun activeAnimeListChanged(index: Int) {
        updateSubstate<AnimeListsState.Authorized> {
            copy(activeAnimeListIndex = index)
        }

        if (animeLists[index].state == AnimeListInternalState.NOT_LOADED) {
            nextActiveAnimeListPage()
        }
    }

    fun openEditAnimeListEntryScreen(animeId: AnimeId) {
        updateSubstate<AnimeListsState.Authorized> {
            val animeListIndex = this.activeAnimeListIndex
            val myListAnimeModel =
                this@AnimeListsViewModel.animeLists[animeListIndex].list
                    .find { it.anime.id == animeId }
                    ?: return

            copy(
                openEditAnimeListEntryScreenEvent = triggered(
                    EditAnimeListEntryRoute(
                        animeBriefDetailsModel = myListAnimeModel.anime,
                        myListStatusModel = myListAnimeModel.status
                    )
                )
            )
        }
    }

    fun openEditAnimeListEntryScreenEventConsumed() {
        updateSubstate<AnimeListsState.Authorized> {
            copy(openEditAnimeListEntryScreenEvent = consumed())
        }
    }

    fun login() {
        viewModelScope.launch {
            val link = getAuthorizeLinkUseCase()

            updateSubstate<AnimeListsState.Unauthorized> { copy(openLinkEvent = triggered(link)) }
        }
    }

    fun openLinkEventConsumed() {
        updateSubstate<AnimeListsState.Unauthorized> { copy(openLinkEvent = consumed()) }
    }

    fun retry() {
        when (state.value) {
            is AnimeListsState.Authorized -> nextActiveAnimeListPage()
            is AnimeListsState.Error -> initialAnimeListLoad()
            else -> {}
        }
    }

    fun nextActiveAnimeListPage() {
        val authorizedState = (state.value as? AnimeListsState.Authorized) ?: return
        val animeListIndex = authorizedState.activeAnimeListIndex

        if (loadPageJobs[animeListIndex] != null) {
            return
        }

        val animeList = animeLists[animeListIndex]
        val nextPage = when (animeList.state) {
            AnimeListInternalState.NOT_LOADED -> 0
            AnimeListInternalState.NEXT_PAGE_AVAILABLE -> animeList.page + 1
            AnimeListInternalState.LOADED -> null
        }

        nextPage?.let {
            loadPage(
                page = nextPage,
                status = animeList.status,
                index = animeListIndex
            )
        }
    }

    private fun findMyListAnimeModel(animeId: AnimeId): MyListAnimeModel? {
        var myListAnimeModel: MyListAnimeModel? = null
        var index = 0

        while (myListAnimeModel == null && index < animeLists.lastIndex) {
            val animeList = animeLists[index]

            myListAnimeModel = animeList.list.find { it.anime.id == animeId }

            index++
        }

        return myListAnimeModel
    }

    private fun deleteAnimeListEntry(animeId: AnimeId) {
        val myListAnimeModel = findMyListAnimeModel(animeId)

        if (myListAnimeModel != null) {
            val animeListIndex = myListAnimeModel.status.status!!.ordinal
            val animeList = animeLists[animeListIndex]

            animeList.list -= myListAnimeModel
            animeList.count--

            updateSubstate<AnimeListsState.Authorized> {
                copy(
                    animeLists = animeLists.map {
                        if (it.status == animeList.status) {
                            animeList.toAnimeListUIModel()
                        } else {
                            it
                        }
                    }
                )
            }
        } else {
            reloadAnimeLists()
        }
    }

    private fun updateLists(updateModel: AnimeListEntryUpdateModel) {
        val myListAnimeModel = findMyListAnimeModel(updateModel.animeId )

        if (myListAnimeModel != null) {
            val oldAnimeListIndex = myListAnimeModel.status.status!!.ordinal
            val newAnimeListIndex = updateModel.status.ordinal
            val oldAnimeList = animeLists[oldAnimeListIndex]

            if (oldAnimeListIndex == newAnimeListIndex) {
                oldAnimeList.list = oldAnimeList.list.map {
                    if (it == myListAnimeModel) {
                        myListAnimeModel.copy(
                            status = myListAnimeModel.status.copy(
                                score = updateModel.score,
                                episodesWatched = updateModel.episodesWatched
                            )
                        )
                    } else {
                        it
                    }
                }
                
                updateSubstate<AnimeListsState.Authorized> {
                    copy(
                        animeLists = animeLists.map { 
                            if (it.status == oldAnimeList.status) {
                                oldAnimeList.toAnimeListUIModel()
                            } else {
                                it
                            }
                        }
                    )
                }
            } else {
                oldAnimeList.count--
                oldAnimeList.list = oldAnimeList.list
                    .toMutableList()
                    .apply {
                        this.remove(myListAnimeModel)
                    }
                    .toList()

                val newAnimeList = animeLists[newAnimeListIndex]

                newAnimeList.count++
                newAnimeList.reset()

                updateSubstate<AnimeListsState.Authorized> {
                    copy(
                        animeLists = animeLists.map {
                            when (it.status) {
                                oldAnimeList.status -> oldAnimeList.toAnimeListUIModel()
                                newAnimeList.status -> newAnimeList.toAnimeListUIModel()
                                else -> it
                            }
                        }
                    )
                }
            }
        } else {
            reloadAnimeLists()
        }
    }

    private fun loadPage(
        page: Int,
        status: ListStatusModel,
        index: Int
    ) {
        updateAnimeListUIEntries(index) {
            this.removeAll { it is ListItemUIModel.Loading || it is ListItemUIModel.Error }
            this.add(ListItemUIModel.Loading)
        }

        loadPageJobs[index] = viewModelScope.launch {
            val result = getMyAnimeListUseCase(
                status = status,
                page = page
            )

            when (result) {
                is UseCaseResult.Success -> {
                    updateAnimeListUIEntries(index) {
                        this.removeAll { it is ListItemUIModel.Loading || it is ListItemUIModel.Error }

                        if (result.value.isEmpty()) {
                            animeLists[index].state = AnimeListInternalState.LOADED
                        } else {
                            animeLists[index].state = AnimeListInternalState.NEXT_PAGE_AVAILABLE
                            animeLists[index].page = page
                            animeLists[index].list += result.value

                            this.addAll(
                                result.value.map {
                                    ListItemUIModel.Item(it.toAnimeListEntryUIModel())
                                }
                            )
                        }
                    }
                }

                is UseCaseResult.Error ->
                    updateAnimeListUIEntries(index) {
                        this.remove(ListItemUIModel.Loading)
                        this.add(
                            ListItemUIModel.Error(result.error.toTextUIModel())
                        )
                    }
            }

            loadPageJobs[index] = null
        }
    }

    private inline fun updateAnimeListUIEntries(
        animeListIndex: Int,
        updater: MutableList<ListItemUIModel<AnimeListEntryUIModel>>.() -> Unit
    ) {
        updateSubstate<AnimeListsState.Authorized> {
            copy(
                animeLists = animeLists.mapIndexed { index, animeListUIModel ->
                    if (index == animeListIndex) {
                        val entries = animeListUIModel.entries.toMutableList()

                        updater(entries)

                        animeListUIModel.copy(entries = entries)
                    } else {
                        animeListUIModel
                    }
                }
            )
        }
    }

    private fun initialAnimeListLoad() {
        viewModelScope.launch {
            updateState { AnimeListsState.Loading }

            val animeStatisticsResult = getAnimeStatisticsUseCase()

            when (animeStatisticsResult) {
                is UseCaseResult.Error ->
                    updateState {
                        AnimeListsState.Error(animeStatisticsResult.error.toErrorUIModel())
                    }

                is UseCaseResult.Success -> {
                    val animeStatistics = animeStatisticsResult.value

                    animeLists.forEach {
                        it.count = when (it.status) {
                            ListStatusModel.WATCHING -> animeStatistics.watchingCount
                            ListStatusModel.COMPLETED -> animeStatistics.completedCount
                            ListStatusModel.ON_HOLD -> animeStatistics.onHoldCount
                            ListStatusModel.DROPPED -> animeStatistics.droppedCount
                            ListStatusModel.PLAN_TO_WATCH -> animeStatistics.planToWatchCount
                        }
                    }

                    updateState {
                        AnimeListsState.Authorized(
                            animeLists = animeLists.map { it.toAnimeListUIModel() }
                        )
                    }

                    nextActiveAnimeListPage()
                }
            }
        }
    }
    
    private fun reloadAnimeLists() {
        animeLists.forEach { it.reset() }
        initialAnimeListLoad()
    }

    private fun MyListAnimeModel.toAnimeListEntryUIModel(): AnimeListEntryUIModel {
        val episodes = this.anime.episodes
        val episodesWatched = this.anime.myListStatus?.episodesWatched
        var progress = 0.5f

        if (episodes != null && episodesWatched != null) {
            progress = episodesWatched / episodes.toFloat()
        }

        return AnimeListEntryUIModel(
            animeId = this.anime.id,
            title = this.anime.title.toTextUIModel(),
            picture = this.anime.mainPicture?.medium,
            type = TextUIModel.from(this.anime.type),
            season = TextUIModel.from(this.anime.season),
            status = TextUIModel.from(this.anime.status),
            episodes = episodes?.toTextUIModel().orUnknownValue(),
            episodesWatched = this.status.episodesWatched.toTextUIModel(),
            progress = progress
        )
    }

    private fun AnimeListInternal.toAnimeListUIModel(): AnimeListUIModel =
        AnimeListUIModel(
            name = TextUIModel.stringResource(
                R.string.anime_list_name,
                TextUIModel.from(this.status),
                this.count
            ),
            status = this.status,
            entries = this.list
                .map { ListItemUIModel.Item(it.toAnimeListEntryUIModel()) }
                .ifEmpty { listOf(ListItemUIModel.Loading) }
        )

    private enum class AnimeListInternalState {
        NOT_LOADED,
        NEXT_PAGE_AVAILABLE,
        LOADED
    }

    private data class AnimeListInternal(
        val status: ListStatusModel,
        var state: AnimeListInternalState = AnimeListInternalState.NOT_LOADED,
        var page: Int = 0,
        var count: Int = 0,
        var list: List<MyListAnimeModel> = emptyList()
    )


    private fun AnimeListInternal.reset() {
        this.state = AnimeListInternalState.NOT_LOADED
        this.page = 0
        this.list = emptyList()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getAuthorizeLinkUseCase: GetAuthorizeLinkUseCase,
        private val getLoggedInFlowUseCase: GetLoggedInFlowUseCase,
        private val getAnimeStatisticsUseCase: GetAnimeStatisticsUseCase,
        private val getMyAnimeListUseCase: GetMyAnimeListUseCase,
        private val getUpdatedAnimeListEntryFlowUseCase: GetUpdatedAnimeListEntryFlowUseCase,
        private val getDeletedAnimeListEntryFlowUseCase: GetDeletedAnimeListEntryFlowUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            AnimeListsViewModel(
                getAuthorizeLinkUseCase = getAuthorizeLinkUseCase,
                getLoggedInFlowUseCase = getLoggedInFlowUseCase,
                getAnimeStatisticsUseCase = getAnimeStatisticsUseCase,
                getMyAnimeListUseCase = getMyAnimeListUseCase,
                getUpdatedAnimeListEntryFlowUseCase = getUpdatedAnimeListEntryFlowUseCase,
                getDeletedAnimeListEntryFlowUseCase = getDeletedAnimeListEntryFlowUseCase
            ) as T
    }
}