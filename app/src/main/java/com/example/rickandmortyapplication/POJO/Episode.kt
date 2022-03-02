package com.example.rickandmortyapplication.POJO

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Episode (

    @PrimaryKey
    @Json(name = "id")
    private val id: Int? = null,

    @Json(name = "name")
    private val name: String? = null,

    @Json(name = "air_date")
    private val airDate: String? = null,

    @Json(name = "episode")
    private val episode: String? = null,

    @Json(name = "characters")
    private val characters: List<String>? = null
)