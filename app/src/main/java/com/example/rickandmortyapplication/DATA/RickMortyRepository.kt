package com.example.rickandmortyapplication.DATA

import androidx.paging.Pager
import androidx.paging.PagingConfig
import io.reactivex.rxjava3.disposables.CompositeDisposable

class RickMortyRepository() {
    fun searchForCharacters() = Pager(
        pagingSourceFactory = { CharacterPagingSource() },
        config = PagingConfig(
            pageSize = 20
        )
    ).flow

    companion object {

        //get repository instance
        fun getInstance() = RickMortyRepository()
    }
}