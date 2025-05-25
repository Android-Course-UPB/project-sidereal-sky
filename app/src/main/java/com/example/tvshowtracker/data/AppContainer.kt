package com.example.tvshowtracker.data

import com.example.tvshowtracker.network.TvMazeApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val tvMazeRepository: TvMazeRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.tvmaze.com/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: TvMazeApiService by lazy {
        retrofit.create(TvMazeApiService::class.java)
    }

    override val tvMazeRepository: TvMazeRepository by lazy {
        NetworkTvMazeRepository(retrofitService)
    }
}

