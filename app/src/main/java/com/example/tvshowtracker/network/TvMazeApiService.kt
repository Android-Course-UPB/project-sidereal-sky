package com.example.tvshowtracker.network

import com.example.tvshowtracker.models.Episode
import com.example.tvshowtracker.models.SearchResult
import com.example.tvshowtracker.models.Show
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvMazeApiService {
    /**
     * Search for TV shows by name
     * @param query The search term to look for
     * @return List of search results containing show information
     */
    @GET("search/shows")
    suspend fun searchShows(@Query("q") query: String): List<SearchResult>

    /**
     * Get all episodes for a specific show
     * @param showId The ID of the show to get episodes for
     * @return List of episodes for the show
     */
    @GET("shows/{id}/episodes")
    suspend fun getEpisodes(@Path("id") showId: Int): List<Episode>

    /**
     * Get detailed information about a specific show
     * @param showId The ID of the show to get details for
     * @return Detailed show information
     */
    @GET("shows/{id}")
    suspend fun getShowDetails(@Path("id") showId: Int): Show
}
