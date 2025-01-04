package com.dima6120.core_api.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T>: ViewModel() {

    val state by lazy { mutableStateOf(createInitialState()) }

    abstract fun createInitialState(): T

    protected inline fun updateState(updater: T.() -> T) {
        state.value = updater(state.value)
    }

    @Suppress("UNCHECKED_CAST")
    protected inline fun <S> updateSubstate(updater: S.() -> T) where S: T =
        updateState {
            (this as? S)?.let(updater) ?: this
        }
}