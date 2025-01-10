package com.dima6120.core_impl.usecase

import com.dima6120.core_api.network.repository.LoginRepository
import com.dima6120.core_api.usecase.GetAuthorizeLinkUseCase
import javax.inject.Inject

class GetAuthorizeLinkUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository
): GetAuthorizeLinkUseCase {

    override fun invoke(): String = loginRepository.getOAuth2AuthorizeURL()
}