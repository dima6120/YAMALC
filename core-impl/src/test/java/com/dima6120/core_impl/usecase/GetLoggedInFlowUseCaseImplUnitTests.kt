package com.dima6120.core_impl.usecase

import com.dima6120.core_api.network.repository.LoginRepository
import com.dima6120.core_impl.usecase.GetLoggedInFlowUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import org.junit.Test

class GetLoggedInFlowUseCaseImplUnitTests {

    private val loginRepository = mockk<LoginRepository>(relaxed = true)

    private val getLoggedInFlowUseCaseImpl = GetLoggedInFlowUseCaseImpl(loginRepository)

    @Test
    fun invoke() {
        // given
        val loggedInFlow = mockk<Flow<Boolean>>(relaxed = true)

        every { loginRepository.getLoggedInFlow() } returns loggedInFlow

        // when
        val givenFlow = getLoggedInFlowUseCaseImpl()

        // then
        verify { loginRepository.getLoggedInFlow() }
        assert(givenFlow == loggedInFlow)
    }
}