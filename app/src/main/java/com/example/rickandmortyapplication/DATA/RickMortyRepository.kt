package com.example.rickandmortyapplication.DATA

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.rickandmortyapplication.API.ApiPagingService
import io.reactivex.rxjava3.disposables.CompositeDisposable

class RickMortyRepository() {
    val apiService = ApiPagingService.getService()

    fun searchForCharacters() = Pager(
        pagingSourceFactory = { CharacterPagingSource(apiService) },
        config = PagingConfig(
            pageSize = 20
        )
    ).flow

    companion object {
        //get repository instance
        fun getInstance() = RickMortyRepository()
    }
}