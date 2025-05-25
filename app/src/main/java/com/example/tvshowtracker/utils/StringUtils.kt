package com.example.tvshowtracker.utils

fun String.removeHtmlTags(): String = this.replace(Regex("<.*?>"), "") 