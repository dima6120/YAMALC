package com.dima6120.core_impl.network.repository

import com.dima6120.core_api.model.anime.AnimeDetailsModel
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.core_api.model.profile.ProfileModel
import com.dima6120.core_api.network.repository.ApiRepository
import com.dima6120.core_impl.error.InternalErrorHandler
import com.dima6120.core_impl.network.model.anime.AnimeDetails
import com.dima6120.core_impl.network.model.anime.toAnimeDetailsModel
import com.dima6120.core_impl.network.model.user.toProfileModel
import com.dima6120.core_impl.network.service.ApiService
import kotlinx.serialization.SerialName
import javax.inject.Inject
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.declaredMembers

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
                fields = ANIME_FIELDS
            ).toAnimeDetailsModel()
        }

    companion object {

        private val ANIME_FIELDS = AnimeDetails::class
            .declaredMemberProperties
            .flatMap { property ->
                property.annotations.mapNotNull { (it as? SerialName)?.value }
            }
            .joinToString(separator = ",")
    }
}