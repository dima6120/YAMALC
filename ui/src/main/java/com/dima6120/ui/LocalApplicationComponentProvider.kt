package com.dima6120.ui

import androidx.compose.runtime.staticCompositionLocalOf
import com.dima6120.core_api.ApplicationComponentProvider

val LocalApplicationComponentProvider =
    staticCompositionLocalOf<ApplicationComponentProvider> { error("No ApplicationComponentProvider") }