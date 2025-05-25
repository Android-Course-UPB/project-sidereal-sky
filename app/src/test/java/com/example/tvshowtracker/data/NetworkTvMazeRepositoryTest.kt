package com.example.tvshowtracker.data

import com.example.tvshowtracker.models.Episode
import com.example.tvshowtracker.models.Show
import com.example.tvshowtracker.models.Image
import com.example.tvshowtracker.models.SearchResult
import com.example.tvshowtracker.network.TvMazeApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkTvMazeRepositoryTest {
    private lateinit var repository: NetworkTvMazeRepository
    private lateinit var mockApiService: TvMazeApiService

    @Before
    fun setup() {
        mockApiService = mock()
        repository = NetworkTvMazeRepository(mockApiService)
    }

    @Test
    fun searchShows_returnsResultsFromApiService() = runTest {
        val query = "test"
        val expectedResults = listOf(
            SearchResult(
                score = 0.9,
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
        whenever(mockApiService.searchShows(query)).thenReturn(expectedResults)

        val actualResults = repository.searchShows(query)

        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getShowDetails_returnsShowFromApiService() = runTest {
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
        whenever(mockApiService.getShowDetails(showId)).thenReturn(expectedShow)

        val actualShow = repository.getShowDetails(showId)

        assertEquals(expectedShow, actualShow)
    }

    @Test
    fun getEpisodes_returnsEpisodesFromApiService() = runTest {
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
        whenever(mockApiService.getEpisodes(showId)).thenReturn(expectedEpisodes)

        val actualEpisodes = repository.getEpisodes(showId)

        assertEquals(expectedEpisodes, actualEpisodes)
    }
} 