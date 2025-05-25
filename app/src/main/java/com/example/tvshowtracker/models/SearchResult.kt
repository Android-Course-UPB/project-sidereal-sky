package com.example.tvshowtracker.models

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val score: Double,
    val show: Show
)
