package com.dima6120.core_impl.network.repository

import com.dima6120.core_api.model.anime.AnimeBriefDetailsModel
import com.dima6120.core_api.model.anime.AnimeDetailsModel
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.model.profile.ProfileModel
import com.dima6120.core_api.network.repository.ApiRepository
import com.dima6120.core_impl.error.InternalErrorHandler
import com.dima6120.core_impl.network.model.anime.AnimeBriefDetails
import com.dima6120.core_impl.network.model.anime.AnimeDetails
import com.dima6120.core_impl.network.model.anime.toAnimeBriefDetailsModel
import com.dima6120.core_impl.network.model.anime.toAnimeDetailsModel
import com.dima6120.core_impl.network.model.user.toProfileModel
import com.dima6120.core_impl.network.service.ApiService
import kotlinx.serialization.SerialName
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties

class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val internalErrorHandler: InternalErrorHandler
): ApiRepository {

    override suspend fun getProfile(): ProfileModel =
        internalErrorHandler.run {
            apiService.getProfile().toProfileModel()
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

    companion object {

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