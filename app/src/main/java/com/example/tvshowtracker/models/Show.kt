package com.example.tvshowtracker.models

import kotlinx.serialization.Serializable

@Serializable
data class Show(
    val id: Int,
    val name: String,
    val summary: String?,
    val image: Image?
)
