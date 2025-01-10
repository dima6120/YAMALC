package com.dima6120.core_impl.network.repository

import com.dima6120.core_api.coroutines.JobSynchronizer
import com.dima6120.core_api.model.anime.AnimeBriefDetailsModel
import com.dima6120.core_api.model.anime.AnimeDetailsModel
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.model.mylist.AnimeListEntryUpdateModel
import com.dima6120.core_api.model.mylist.ListStatusModel
import com.dima6120.core_api.model.mylist.MyListAnimeModel
import com.dima6120.core_api.model.profile.ProfileModel
import com.dima6120.core_api.network.repository.ApiRepository
import com.dima6120.core_impl.error.InternalErrorHandler
import com.dima6120.core_impl.network.model.anime.AnimeBriefDetails
import com.dima6120.core_impl.network.model.anime.AnimeDetails
import com.dima6120.core_impl.network.model.anime.toAnimeBriefDetailsModel
import com.dima6120.core_impl.network.model.anime.toAnimeDetailsModel
import com.dima6120.core_impl.network.model.mylist.toMyAnimeListModel
import com.dima6120.core_impl.network.model.mylist.toStringValue
import com.dima6120.core_impl.network.model.user.toProfileModel
import com.dima6120.core_impl.network.service.ApiService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.SerialName
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties

class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val internalErrorHandler: InternalErrorHandler
): ApiRepository {

    private val profileFlow = MutableStateFlow<ProfileModel?>(null)
    private val updatedAnimeListEntryFlow = MutableSharedFlow<AnimeListEntryUpdateModel>()
    private val deletedAnimeListEntryFlow = MutableSharedFlow<AnimeId>()
    private val profileJobSynchronizer = JobSynchronizer<ProfileModel>()

    override fun getUpdatedAnimeListEntryFlow(): SharedFlow<AnimeListEntryUpdateModel> =
        updatedAnimeListEntryFlow.asSharedFlow()

    override fun getDeletedAnimeListEntryFlow(): SharedFlow<AnimeId> =
        deletedAnimeListEntryFlow.asSharedFlow()

    override fun getProfileFlow(): StateFlow<ProfileModel?> = profileFlow.asStateFlow()

    override suspend fun getProfile(force: Boolean): ProfileModel =
        internalErrorHandler.run {
            profileJobSynchronizer.runOrJoin {
                profileFlow.value.takeUnless { force } ?: let {
                    val profileModel = apiService.getProfile().toProfileModel()

                    profileFlow.value = profileModel

                    profileModel
                }
            }
        }

    override suspend fun getAnimeDetails(animeId: AnimeId): AnimeDetailsModel =
        internalErrorHandler.run {
            apiService.getAnimeDetails(
                animeId = animeId.id,
                fields = ANIME_DETAILS_FIELDS
            ).toAnimeDetailsModel()
        }

    override suspend fun getAnimeList(query: String, page: Int): List<AnimeBriefDetailsModel> =
        internalErrorHandler.run {
            apiService.getAnimeList(
                query = query,
                limit = ANIME_LIST_PAGE_LIMIT,
                offset = page * ANIME_LIST_PAGE_LIMIT,
                fields = ANIME_BRIEF_DETAILS_FIELS
            ).data.map { it.animeBriefDetails.toAnimeBriefDetailsModel() }
        }

    override suspend fun getMyAnimeList(status: ListStatusModel, page: Int): List<MyListAnimeModel> =
        internalErrorHandler.run {
            apiService.getUserAnimeList(
                user = CURRENT_USER,
                status = status.toStringValue(),
                sort = ANIME_SORT,
                limit = ANIME_LIST_PAGE_LIMIT,
                offset = page * ANIME_LIST_PAGE_LIMIT,
                fields = ANIME_BRIEF_DETAILS_FIELS
            ).data.map { it.toMyAnimeListModel() }
        }

    override suspend fun updateAnimeListEntry(animeListEntryUpdateModel: AnimeListEntryUpdateModel) =
        internalErrorHandler.run {
            apiService.updateAnimeListEntry(
                animeId = animeListEntryUpdateModel.animeId.id,
                status = animeListEntryUpdateModel.status.toStringValue(),
                score = animeListEntryUpdateModel.score,
                watchedEpisodes = animeListEntryUpdateModel.episodesWatched
            )

            updatedAnimeListEntryFlow.emit(animeListEntryUpdateModel)
        }

    override suspend fun deleteAnimeListEntry(animeId: AnimeId) =
        internalErrorHandler.run {
            apiService.deleteAnimeListEntry(animeId.id)
            deletedAnimeListEntryFlow.emit(animeId)
        }

    companion object {

        private val CURRENT_USER = "@me"

        private val ANIME_SORT = "anime_title"

        private val ANIME_LIST_PAGE_LIMIT = 15

        private val ANIME_DETAILS_FIELDS = AnimeDetails::class.getFields()

        private val ANIME_BRIEF_DETAILS_FIELS = AnimeBriefDetails::class.getFields()

        private fun <T : Any> KClass<T>.getFields(): String =
            this.declaredMemberProperties
                .flatMap { property ->
                    property.annotations.mapNotNull { (it as? SerialName)?.value }
                }
                .joinToString(separator = ",")
    }
}