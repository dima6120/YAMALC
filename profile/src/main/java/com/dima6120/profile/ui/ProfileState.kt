package com.dima6120.profile.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.dima6120.core_api.error.YamalcError
import com.dima6120.ui.models.ErrorUIModel
import com.dima6120.ui.models.TextUIModel
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

sealed class ProfileState {
    data object Loading: ProfileState()

    data class Error(val error: ErrorUIModel): ProfileState()

    data class Authorized(
        val userInfo: UserInfoUIModel,
        val animeStatistics: AnimeStatisticsUIModel
    ): ProfileState()

    data class Unauthorized(
        val openLinkEvent: StateEventWithContent<String> = consumed()
    ): ProfileState()
}

data class UserInfoUIModel(
    val name: String,
    val picture: String,
    val gender: GenderUIModel?,
    val birthday: String?,
    val location: String?,
    val joinedAt: String,
)

data class GenderUIModel(
    @DrawableRes val icon: Int,
    val name: TextUIModel
)

data class AnimeStatisticsUIModel(
    val days: Float,
    val completedCount: Int,
    val meanScore: Float,
    val watchingWeight: Float,
    val completedWeight: Float,
    val onHoldWeight: Float,
    val droppedWeight: Float,
    val planToWatchWeight: Float
)