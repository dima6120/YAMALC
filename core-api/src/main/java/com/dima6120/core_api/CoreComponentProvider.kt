package com.dima6120.core_api

import com.dima6120.core_api.coroutines.DispatchersProvider
import com.dima6120.core_api.network.NetworkProvider
import com.dima6120.core_api.storage.StorageProvider
import com.dima6120.core_api.usecase.UseCaseProvider
import com.dima6120.core_api.utils.UtilsProvider

interface CoreComponentProvider:
    NetworkProvider,
    StorageProvider,
    UtilsProvider,
    DispatchersProvider,
    UseCaseProvider