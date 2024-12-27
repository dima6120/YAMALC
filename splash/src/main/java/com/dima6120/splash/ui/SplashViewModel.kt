package com.dima6120.splash.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class SplashViewModel @Inject constructor(): ViewModel() {

    var state by mutableStateOf(SplashState())
        private set

    init {
        viewModelScope.launch {
            delay(2000)

            updateState { copy(navigateToMainRouteEvent = triggered) }
        }
    }

    fun navigateToMainRouteEventConsumed() {
        updateState { copy(navigateToMainRouteEvent = consumed) }
    }

    override fun onCleared() {
        super.onCleared()
    }

    private inline fun updateState(updater: SplashState.() -> SplashState) {
        state = updater(state)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SplashViewModel() as T
    }
}