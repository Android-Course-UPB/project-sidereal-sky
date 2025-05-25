package com.example.tvshowtracker.models

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val medium: String?,
    val original: String?
)
