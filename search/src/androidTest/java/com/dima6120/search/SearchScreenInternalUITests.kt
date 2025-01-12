package com.dima6120.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import com.dima6120.core_api.model.anime.AnimeId
import com.dima6120.search.ui.SearchResults
import com.dima6120.search.ui.SearchScreenInternal
import com.dima6120.search.ui.SearchScreenState
import com.dima6120.search.utils.TestTags
import com.dima6120.ui.theme.YAMALCTheme
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class SearchScreenInternalUITests {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val initialState = SearchScreenState()

    private interface ScreenCallbacks {
        fun search()
        fun searchWithGivenQuery(query: String)
        fun clearSearch()
        fun queryChanged(query: String)
        fun onAnimeItemClick(animeId: AnimeId)
        fun onLoadNextPage()
        fun onRetryLoadPageClick()
    }

    private val screenCallbacks = mockk<ScreenCallbacks>(relaxed = true)

    private val query = "query"

    private val resentSearchesNode: SemanticsNodeInteraction
        get() = composeTestRule.onNodeWithTag(TestTags.ResentSearches)

    private val searchFieldNode: SemanticsNodeInteraction
        get() = composeTestRule.onNodeWithTag(TestTags.SearchField)

    @Test
    fun search() {
        composeTestRule.setContent {
            var state by remember { mutableStateOf(initialState) }

            YAMALCTheme {
                SearchScreenInternal(
                    state = state,
                    search = screenCallbacks::search,
                    searchWithGivenQuery = screenCallbacks::searchWithGivenQuery,
                    clearSearch = screenCallbacks::clearSearch,
                    queryChanged = {
                        state = state.copy(
                            query = it.trim(),
                            queries = state.queries + query,
                            searchResults = SearchResults.Loading
                        )
                    },
                    onAnimeItemClick = screenCallbacks::onAnimeItemClick,
                    onLoadNextPage = screenCallbacks::onLoadNextPage,
                    onRetryLoadPageClick = screenCallbacks::onRetryLoadPageClick
                )
            }
        }

        resentSearchesNode.assertDoesNotExist()

        searchFieldNode.performClick()

        resentSearchesNode.assertExists()

        searchFieldNode.performTextInput(query)

        composeTestRule.waitForIdle()

        searchFieldNode.assert(hasText(query))

        searchFieldNode.performImeAction()

        verify { screenCallbacks.search() }
    }
}