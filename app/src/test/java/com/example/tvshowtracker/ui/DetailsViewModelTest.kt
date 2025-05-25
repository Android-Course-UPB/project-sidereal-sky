package com.example.tvshowtracker.ui

import com.example.tvshowtracker.data.TvMazeRepository
import com.example.tvshowtracker.models.Episode
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
class DetailsViewModelTest {
    private lateinit var viewModel: DetailsViewModel
    private lateinit var mockRepository: TvMazeRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = mock()
        viewModel = DetailsViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadShowDetails_updatesShowDetailsCorrectly() = runTest {
        val showId = 1
        val expectedShow = Show(
            id = showId,
            name = "Test Show",
            summary = "Test summary",
            image = Image(
                medium = "https://example.com/medium.jpg",
                original = "https://example.com/original.jpg"
            )
        )
        whenever(mockRepository.getShowDetails(showId)).thenReturn(expectedShow)

        viewModel.loadShowDetails(showId)
        testDispatcher.scheduler.advanceUntilIdle()

        val actualShow = viewModel.showDetails.first()
        assertEquals(expectedShow, actualShow)
    }

    @Test
    fun loadEpisodes_updatesEpisodesListCorrectly() = runTest {
        val showId = 1
        val expectedEpisodes = listOf(
            Episode(
                id = 1,
                name = "Test Episode 1",
                season = 1,
                number = 1,
                summary = "Test episode summary",
                image = Image(
                    medium = "https://example.com/medium.jpg",
                    original = "https://example.com/original.jpg"
                )
            )
        )
        whenever(mockRepository.getEpisodes(showId)).thenReturn(expectedEpisodes)

        viewModel.loadEpisodes(showId)
        testDispatcher.scheduler.advanceUntilIdle()

        val actualEpisodes = viewModel.episodes.first()
        assertEquals(expectedEpisodes, actualEpisodes)
    }

    @Test
    fun loadAll_loadsBothShowDetailsAndEpisodes() = runTest {
        val showId = 1
        val expectedShow = Show(
            id = showId,
            name = "Test Show",
            summary = "Test summary",
            image = Image(
                medium = "https://example.com/medium.jpg",
                original = "https://example.com/original.jpg"
            )
        )
        val expectedEpisodes = listOf(
            Episode(
                id = 1,
                name = "Test Episode 1",
                season = 1,
                number = 1,
                summary = "Test episode summary",
                image = Image(
                    medium = "https://example.com/medium.jpg",
                    original = "https://example.com/original.jpg"
                )
            )
        )
        whenever(mockRepository.getShowDetails(showId)).thenReturn(expectedShow)
        whenever(mockRepository.getEpisodes(showId)).thenReturn(expectedEpisodes)

        viewModel.loadAll(showId)
        testDispatcher.scheduler.advanceUntilIdle()

        val actualShow = viewModel.showDetails.first()
        val actualEpisodes = viewModel.episodes.first()
        assertEquals(expectedShow, actualShow)
        assertEquals(expectedEpisodes, actualEpisodes)
    }
} 