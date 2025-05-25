package com.example.tvshowtracker.ui

import com.example.tvshowtracker.data.TvMazeRepository
import com.example.tvshowtracker.models.SearchResult
import com.example.tvshowtracker.models.Show
import com.example.tvshowtracker.models.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    private lateinit var viewModel: SearchViewModel
    private lateinit var mockRepository: TvMazeRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = mock()
        viewModel = SearchViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun search_updatesResultsCorrectly() = runTest {
        val query = "test"
        val expectedResults = listOf(
            SearchResult(
                score = 1.0,
                show = Show(
                    id = 1,
                    name = "Test Show",
                    summary = "Test summary",
                    image = Image(
                        medium = "https://example.com/medium.jpg",
                        original = "https://example.com/original.jpg"
                    )
                )
            )
        )
        whenever(mockRepository.searchShows(query)).thenReturn(expectedResults)

        viewModel.search(query)
        testDispatcher.scheduler.advanceUntilIdle()

        val actualResults = viewModel.searchResults.first()
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun search_withEmptyQuery_returnsEmptyList() = runTest {
        val query = ""
        whenever(mockRepository.searchShows(query)).thenReturn(emptyList())

        viewModel.search(query)
        testDispatcher.scheduler.advanceUntilIdle()

        val actualResults = viewModel.searchResults.first()
        assertEquals(emptyList<SearchResult>(), actualResults)
    }
} 