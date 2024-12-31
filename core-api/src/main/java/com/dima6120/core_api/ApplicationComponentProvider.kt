package com.dima6120.core_api

import com.dima6120.core_api.navigation.NavGraphsProvider

interface ApplicationComponentProvider:
    ApplicationContextProvider,
    NavGraphsProvider,
    CoreComponentProvider