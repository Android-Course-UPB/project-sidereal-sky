package com.example.tvshowtracker.data

import com.example.tvshowtracker.models.Episode
import com.example.tvshowtracker.models.SearchResult
import com.example.tvshowtracker.models.Show
import com.example.tvshowtracker.network.TvMazeApiService

interface TvMazeRepository {
    suspend fun searchShows(query: String): List<SearchResult>

    suspend fun getShowDetails(showId: Int): Show

    suspend fun getEpisodes(showId: Int): List<Episode>
}

/**
 * Network Implementation of Repository that fetch mars photos list from marsApi.
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

