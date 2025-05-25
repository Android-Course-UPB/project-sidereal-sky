package com.example.tvshowtracker.data

import com.example.tvshowtracker.models.Episode
import com.example.tvshowtracker.models.SearchResult
import com.example.tvshowtracker.models.Show
import com.example.tvshowtracker.network.TvMazeApiService

interface TvMazeRepository {
    /**
     * Search for TV shows by name
     * @param query The search term to look for
     * @return List of search results containing show information
     */
    suspend fun searchShows(query: String): List<SearchResult>

    /**
     * Get detailed information about a specific show
     * @param showId The ID of the show to get details for
     * @return Detailed show information
     */
    suspend fun getShowDetails(showId: Int): Show

    /**
     * Get all episodes for a specific show
     * @param showId The ID of the show to get episodes for
     * @return List of episodes for the show
     */
    suspend fun getEpisodes(showId: Int): List<Episode>
}

/**
 * Implementation of TvMazeRepository that uses the TV Maze API
 * @param tvMazeApiService The API service to make network requests
 */
class NetworkTvMazeRepository(
    private val tvMazeApiService: TvMazeApiService
) : TvMazeRepository {
    override suspend fun searchShows(query: String): List<SearchResult> {
        return tvMazeApiService.searchShows(query)
    }

    override suspend fun getShowDetails(showId: Int): Show {
        return tvMazeApiService.getShowDetails(showId)
    }

    override suspend fun getEpisodes(showId: Int): List<Episode> {
        return tvMazeApiService.getEpisodes(showId)
    }
}

