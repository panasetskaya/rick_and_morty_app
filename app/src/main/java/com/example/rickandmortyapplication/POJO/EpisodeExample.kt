package com.example.rickandmortyapplication.POJO

import com.squareup.moshi.Json

data class EpisodeExample(
    @Json(name = "results")
    val episodes: List<Episode>? = null
)
