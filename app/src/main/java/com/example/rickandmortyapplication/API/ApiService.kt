package com.example.rickandmortyapplication.API

import com.example.rickandmortyapplication.POJO.Character
import com.example.rickandmortyapplication.POJO.Episode
import com.example.rickandmortyapplication.POJO.EpisodeExample
import com.example.rickandmortyapplication.POJO.Example
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    fun getCharacters(@Query("page") page: Int): Single<Example>

    @GET("episode")
    fun getEpisodes(): Single<EpisodeExample>

}