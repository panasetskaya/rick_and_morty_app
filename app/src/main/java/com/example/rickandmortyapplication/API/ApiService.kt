package com.example.rickandmortyapplication.API

import com.example.rickandmortyapplication.POJO.Character
import com.example.rickandmortyapplication.POJO.Episode
import com.example.rickandmortyapplication.POJO.EpisodeExample
import com.example.rickandmortyapplication.POJO.Example
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiService {

    @GET("character")
    fun getCharacters(): Single<Example>

    @GET("episode")
    fun getEpisodes(): Single<EpisodeExample>

}