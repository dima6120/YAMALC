package com.dima6120.yamalc.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dima6120.core_api.model.getYamalcErrorOrNull
import com.dima6120.core_api.ui.BaseViewModel
import com.dima6120.yamalc.usecase.GetTokenUseCase
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel(
    private val getTokenUseCase: GetTokenUseCase
): BaseViewModel<MainActivityState>() {

    override val initialState = MainActivityState()

    fun getToken(code: String) {
        viewModelScope.launch {
            val error = getTokenUseCase(code).getYamalcErrorOrNull()

            if (error != null) {
                updateState { copy(loginError = triggered(error)) }
            }
        }
    }

    fun loginErrorConsumed() {
        updateState { copy(loginError = consumed()) }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getTokenUseCase: GetTokenUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainActivityViewModel(getTokenUseCase) as T
    }
}