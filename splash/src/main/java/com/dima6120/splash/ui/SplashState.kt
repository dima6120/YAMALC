package com.dima6120.splash.ui

import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed

data class SplashState(
    val navigateToMainRouteEvent: StateEvent = consumed
)
