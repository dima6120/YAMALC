package com.dima6120.core_impl.di

import com.dima6120.core_api.error.ErrorHandler
import com.dima6120.core_impl.error.InternalErrorHandler
import com.dima6120.core_api.utils.DateFormatter
import com.dima6120.core_impl.error.ErrorHandlerImpl
import com.dima6120.core_impl.error.InternalErrorHandlerImpl
import com.dima6120.core_impl.utils.DateFormatterImpl
import dagger.Binds
import dagger.Module

@Module
interface UtilsModule {

    @Binds
    fun provideErrorHandler(impl: ErrorHandlerImpl): ErrorHandler

    @Binds
    fun provideInternalErrorHandler(impl: InternalErrorHandlerImpl): InternalErrorHandler

    @Binds
    fun provideDateFormatter(impl: DateFormatterImpl): DateFormatter
}