package com.dima6120.core_impl.network.model.user

import com.dima6120.core_api.model.GenderModel
import com.dima6120.core_api.model.ProfileModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(

    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("picture")
    val picture: String,

    @SerialName("gender")
    val gender: String? = null,

    @SerialName("birthday")
    val birthday: String? = null,

    @SerialName("location")
    val location: String? = null,

    @SerialName("joined_at")
    val joinedAt: String,

    @SerialName("anime_statistics")
    val animeStatistics: AnimeStatistics,
)

fun Profile.toProfileModel(): ProfileModel =
    ProfileModel(
        id = this.id,
        name = this.name,
        picture = this.picture,
        gender = GenderModel.fromValue(this.gender),
        birthday = this.birthday,
        location = this.location,
        joinedAt = this.joinedAt,
        animeStatistics = this.animeStatistics.toAnimeStatisticsModel()
    )
