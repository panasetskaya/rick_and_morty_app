package com.example.rickandmortyapplication.POJO

import com.squareup.moshi.Json

data class Location (
    @Json(name = "name")
    private val name: String? = null
)