package com.example.rickandmortyapplication.DATA

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.rickandmortyapplication.API.ApiPagingService
import io.reactivex.rxjava3.disposables.CompositeDisposable

class RickMortyRepository(val context: Context) {
    val apiService = ApiPagingService.getService()

    fun searchForCharacters() = Pager(
        pagingSourceFactory = { CharacterPagingSource(apiService, context) },
        config = PagingConfig(
            pageSize = 20
        )
    ).flow

    companion object {
        //get repository instance
        fun getInstance(context: Context) = RickMortyRepository(context)
    }
}