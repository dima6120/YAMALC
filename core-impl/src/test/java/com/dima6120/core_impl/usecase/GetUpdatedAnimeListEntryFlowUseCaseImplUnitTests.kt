package com.dima6120.core_impl.usecase

import com.dima6120.core_api.model.mylist.AnimeListEntryUpdateModel
import com.dima6120.core_api.network.repository.ApiRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.SharedFlow
import org.junit.Test

class GetUpdatedAnimeListEntryFlowUseCaseImplUnitTests {

    private val apiRepository = mockk<ApiRepository>(relaxed = true)

    private val getUpdatedAnimeListEntryFlowUseCaseImpl = GetUpdatedAnimeListEntryFlowUseCaseImpl(apiRepository)

    @Test
    fun invoke() {
        // given
        val updatedAnimeListEntryFlow = mockk<SharedFlow<AnimeListEntryUpdateModel>>(relaxed = true)

        every { apiRepository.getUpdatedAnimeListEntryFlow() } returns updatedAnimeListEntryFlow

        // when
        val givenFlow = getUpdatedAnimeListEntryFlowUseCaseImpl()

        // then
        verify { apiRepository.getUpdatedAnimeListEntryFlow() }
        assert(givenFlow == updatedAnimeListEntryFlow)
    }
}