package com.dima6120.core_api.utils

import com.dima6120.core_api.error.ErrorHandler
import com.dima6120.core_api.utils.DateFormatter

interface UtilsProvider {

    fun provideDateFormatter(): DateFormatter

    fun provideErrorHandler(): ErrorHandler
}