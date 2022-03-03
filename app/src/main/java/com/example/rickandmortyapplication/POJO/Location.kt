package com.example.rickandmortyapplication.POJO

import com.squareup.moshi.Json

data class Location (
    @Json(name = "name")
    val name: String? = null
)