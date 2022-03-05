package com.example.rickandmortyapplication.DATA

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmortyapplication.POJO.Character
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.Flow

class RickMortyViewModel(application: Application): AndroidViewModel(application) {

    val compositeDisposable = CompositeDisposable()

    private val repository: RickMortyRepository = RickMortyRepository.getInstance(application)

    fun getCharacters(): Flow<PagingData<Character>> {
        Log.i("MyRes", "RickMortyViewModel.getCharacters() working")
        val result = repository.searchForCharacters()
            .cachedIn(viewModelScope)
        Log.i("MyRes", "RickMortyViewModel.getCharacters() result(Flow<PagingData<Character>>): "+result.toString())
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}