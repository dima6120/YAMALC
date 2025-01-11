package com.dima6120.profile.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dima6120.core_api.model.profile.AnimeStatisticsModel
import com.dima6120.core_api.model.profile.GenderModel
import com.dima6120.core_api.model.profile.ProfileModel
import com.dima6120.core_api.model.UseCaseResult
import com.dima6120.core_api.utils.DateFormatter
import com.dima6120.profile.R
import com.dima6120.core_api.usecase.GetAuthorizeLinkUseCase
import com.dima6120.core_api.usecase.GetLoggedInFlowUseCase
import com.dima6120.profile.usecase.GetProfileFlowUseCase
import com.dima6120.profile.usecase.GetProfileUseCase
import com.dima6120.profile.usecase.LogoutUseCase
import com.dima6120.ui.models.TextUIModel
import com.dima6120.ui.models.toErrorUIModel
import com.dima6120.ui.models.toTextUIModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max

class ProfileViewModel(
    private val getAuthorizeLinkUseCase: GetAuthorizeLinkUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val getLoggedInFlowUseCase: GetLoggedInFlowUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getProfileFlowUseCase: GetProfileFlowUseCase,
    private val dateFormatter: DateFormatter
): ViewModel() {

    var state by mutableStateOf<ProfileState>(ProfileState.Loading)
        private set

    private var collectProfileFlowJob: Job? = null

    init {
        viewModelScope.launch {
            getLoggedInFlowUseCase()
                .collect { loggedIn ->
                    if (loggedIn) {
                        loadProfile()
                    } else {
                        stopCollectingProfileFlow()
                        updateState { ProfileState.Unauthorized() }
                    }
                }
        }
    }

    fun loadProfile() {
        viewModelScope.launch {
            updateState {
                ProfileState.Loading
            }

            when (val result = getProfileUseCase()) {
                is UseCaseResult.Error ->
                    updateState {
                        ProfileState.Error(
                            error = result.error.toErrorUIModel()
                        )
                    }

                is UseCaseResult.Success -> {
                    updateState {
                        ProfileState.Authorized(
                            userInfo = result.value.toUserInfoUIModel(),
                            animeStatistics = result.value.animeStatistics.toAnimeStatisticsUIModel()
                        )
                    }

                    collectProfileFlow()
                }
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            val link = getAuthorizeLinkUseCase()

            updateUnauthorizedState { copy(openLinkEvent = triggered(link)) }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    fun openLinkEventConsumed() {
        updateUnauthorizedState { copy(openLinkEvent = consumed()) }
    }

    private fun stopCollectingProfileFlow() {
        collectProfileFlowJob?.cancel()
        collectProfileFlowJob = null
    }

    private fun collectProfileFlow() {
        collectProfileFlowJob?.cancel()
        collectProfileFlowJob = viewModelScope.launch {
            getProfileFlowUseCase().collect {
                updateState {
                    ProfileState.Authorized(
                        userInfo = it.toUserInfoUIModel(),
                        animeStatistics = it.animeStatistics.toAnimeStatisticsUIModel()
                    )
                }
            }
        }
    }

    private fun ProfileModel.toUserInfoUIModel(): UserInfoUIModel =
        UserInfoUIModel(
            name = this.name.toTextUIModel(),
            picture = this.picture,
            gender = this.gender.toGenderUIModel(),
            birthday = this.birthday?.let(dateFormatter::formatDate)?.toTextUIModel(),
            location = this.location?.toTextUIModel(),
            joinedAt = this.joinedAt.let(dateFormatter::formatDateTime).orEmpty().toTextUIModel(),
        )

    private fun GenderModel.toGenderUIModel(): GenderUIModel? =
        when (this) {
            GenderModel.MALE ->
                GenderUIModel(
                    icon = R.drawable.ic_male,
                    name = TextUIModel.stringResource(R.string.male_gender)
                )

            GenderModel.FEMALE ->
                GenderUIModel(
                    icon = R.drawable.ic_female,
                    name = TextUIModel.stringResource(R.string.female_gender)
                )

            GenderModel.NONE -> null
        }

    private fun AnimeStatisticsModel.toAnimeStatisticsUIModel(): AnimeStatisticsUIModel =
        AnimeStatisticsUIModel(
            days = this.days,
            completedCount = this.completedCount,
            meanScore = this.meanScore,
            watchingWeight = computeWeight(this.watchingCount, this.count),
            completedWeight = computeWeight(this.completedCount, this.count),
            onHoldWeight = computeWeight(this.onHoldCount, this.count),
            droppedWeight = computeWeight(this.droppedCount, this.count),
            planToWatchWeight = computeWeight(this.planToWatchCount, this.count)
        )

    private fun computeWeight(value: Int, count: Int): Float =
        max(1, (value.toFloat() / count * 100f).toInt()).toFloat()

    private inline fun updateUnauthorizedState(updater: ProfileState.Unauthorized.() -> ProfileState) {
        updateState {
            (this as? ProfileState.Unauthorized)?.let(updater) ?: this
        }
    }

    private inline fun updateState(updater: ProfileState.() -> ProfileState) {
        state = updater(state)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getAuthorizeLinkUseCase: GetAuthorizeLinkUseCase,
        private val getProfileUseCase: GetProfileUseCase,
        private val getLoggedInFlowUseCase: GetLoggedInFlowUseCase,
        private val logoutUseCase: LogoutUseCase,
        private val dateFormatter: DateFormatter,
        private val getProfileFlowUseCase: GetProfileFlowUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ProfileViewModel(
                getAuthorizeLinkUseCase,
                getProfileUseCase,
                getLoggedInFlowUseCase,
                logoutUseCase,
                getProfileFlowUseCase,
                dateFormatter
            ) as T
    }
}