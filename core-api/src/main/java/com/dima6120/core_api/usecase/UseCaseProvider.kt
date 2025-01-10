package com.dima6120.core_api.usecase

interface UseCaseProvider {

    fun provideGetAuthorizeLinkUseCase(): GetAuthorizeLinkUseCase
    fun provideGetLoggedInFlowUseCase(): GetLoggedInFlowUseCase
}