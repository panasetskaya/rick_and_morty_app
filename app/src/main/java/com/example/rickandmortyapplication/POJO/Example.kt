package com.example.rickandmortyapplication.POJO

import com.squareup.moshi.Json

data class Example (
    @Json(name = "results")
    val characters: List<Character>? = null
)