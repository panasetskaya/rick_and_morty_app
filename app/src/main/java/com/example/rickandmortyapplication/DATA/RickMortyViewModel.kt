package com.example.rickandmortyapplication.DATA

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmortyapplication.POJO.Character
import kotlinx.coroutines.flow.Flow

class RickMortyViewModel(private val repository: RickMortyRepository = RickMortyRepository.getInstance()): ViewModel() {

    fun getCharacters(): Flow<PagingData<Character>> {
        Log.i("MyRes", "RickMortyViewModel.getCharacters() working")
        val result = repository.searchForCharacters()
            .cachedIn(viewModelScope)
        return result
    }
}