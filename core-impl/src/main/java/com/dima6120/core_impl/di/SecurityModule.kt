package com.dima6120.core_impl.di

import com.dima6120.core_impl.security.manager.CryptoManager
import com.dima6120.core_impl.security.manager.CryptoManagerImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface SecurityModule {

    @Singleton
    @Binds
    fun provideCryptoManager(impl: CryptoManagerImpl): CryptoManager
}