package com.example.tvshowtracker.network

import com.example.tvshowtracker.models.Episode
import com.example.tvshowtracker.models.SearchResult
import com.example.tvshowtracker.models.Show
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvMazeApiService {
    @GET("search/shows")
    suspend fun searchShows(@Query("q") query: String): List<SearchResult>

    @GET("shows/{id}/episodes")
    suspend fun getEpisodes(@Path("id") showId: Int): List<Episode>

    @GET("shows/{id}")
    suspend fun getShowDetails(@Path("id") showId: Int): Show
}
