package com.example.rickandmortyapplication.POJO

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Episode (

    @PrimaryKey
    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "air_date")
    val airDate: String? = null,

    @Json(name = "episode")
    val episode: String? = null,

    @Json(name = "characters")
    val characters: List<String>? = null
)