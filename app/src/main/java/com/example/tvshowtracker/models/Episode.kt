package com.example.tvshowtracker.models

import kotlinx.serialization.Serializable

@Serializable
data class Episode(
    val id: Int,
    val name: String,
    val season: Int,
    val number: Int,
    val summary: String?,
    val image: Image?
)
