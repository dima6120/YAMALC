package com.dima6120.yamalc.di

import com.dima6120.yamalc.usecase.GetTokenUseCase
import com.dima6120.yamalc.usecase.GetTokenUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun provideGetTokenUseCase(impl: GetTokenUseCaseImpl): GetTokenUseCase
}