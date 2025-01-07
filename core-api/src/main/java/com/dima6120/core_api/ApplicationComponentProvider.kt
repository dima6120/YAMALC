package com.dima6120.core_api

import com.dima6120.core_api.navigation.ScreensProvider

interface ApplicationComponentProvider:
    ApplicationContextProvider,
    ScreensProvider,
    CoreComponentProvider