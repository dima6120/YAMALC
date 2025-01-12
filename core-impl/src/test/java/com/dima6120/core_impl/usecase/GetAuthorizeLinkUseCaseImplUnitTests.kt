package com.dima6120.core_impl.usecase

import com.dima6120.core_api.network.repository.LoginRepository
import com.dima6120.core_impl.usecase.GetAuthorizeLinkUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class GetAuthorizeLinkUseCaseImplUnitTests {

    private val loginRepository = mockk<LoginRepository>(relaxed = true)

    private val getAuthorizeLinkUseCaseImpl = GetAuthorizeLinkUseCaseImpl(loginRepository)

    @Test
    fun invoke() {
        // given
        val url = "url"

        every { loginRepository.getOAuth2AuthorizeURL() } returns url

        // when
        val givenUrl = getAuthorizeLinkUseCaseImpl()

        // then
        verify { loginRepository.getOAuth2AuthorizeURL() }

        assert(givenUrl == url)
    }
}