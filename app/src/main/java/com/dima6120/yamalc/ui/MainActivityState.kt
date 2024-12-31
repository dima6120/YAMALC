package com.dima6120.yamalc.ui

import com.dima6120.core_api.error.YamalcError
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class MainActivityState(
    val loginError: StateEventWithContent<YamalcError> = consumed()
)
