package com.example.rickandmortyapplication.DATA

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmortyapplication.POJO.Character
import com.example.rickandmortyapplication.POJO.Episode
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.Flow

class RickMortyViewModel(application: Application): AndroidViewModel(application) {

    private val db = RickMortyDatabase.getInstance(application)

    private val repository: RickMortyRepository = RickMortyRepository.getInstance(application)

    fun getCharacters(): Flow<PagingData<Character>> {
        Log.i("MyRes", "RickMortyViewModel.getCharacters() working")
        val result = repository.searchForCharacters()
            .cachedIn(viewModelScope)
        Log.i("MyRes", "RickMortyViewModel.getCharacters() result(Flow<PagingData<Character>>): "+result.toString())
        return result
    }

    fun getEpisodes(url: String): Flow<PagingData<Episode>> {
        val result = repository.searchForEpisodes(url)
            .cachedIn(viewModelScope)
        return result
    }

    fun getCharacterById(requiredId: Int): LiveData<Character> {
        return db.rickMortyDao.getCharacterById(requiredId)
    }
}