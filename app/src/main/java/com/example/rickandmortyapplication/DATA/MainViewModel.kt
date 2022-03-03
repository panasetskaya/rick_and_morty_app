package com.example.rickandmortyapplication.DATA

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.rickandmortyapplication.API.ApiFactory
import com.example.rickandmortyapplication.POJO.Character
import com.example.rickandmortyapplication.POJO.Episode
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val db = RickMortyDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val charactersList = db.rickMortyDao.getAllCharacters()
    val episodesList = db.rickMortyDao.getAllEpisodes()

    fun loadCharacters() {
        val disposable = ApiFactory.apiService.getCharacters()
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it.characters!=null) {
                    for (character in it.characters) {
                        db.rickMortyDao.insertCharacter(character)
                    }
                    Log.i("MyRes", "inserting characters successful")
                } else {Log.i("MyRes", "inserting failed, characters = null")}
            }, {
                Log.i("MyRes", "loading characters failed: "+it.message)
                Toast.makeText(getApplication(), "Ошибка загрузки", Toast.LENGTH_SHORT).show()
            })
        compositeDisposable.add(disposable)
    }

    fun loadEpisodes() {
        val disposable = ApiFactory.apiService.getEpisodes()
            .delaySubscription(600, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it.episodes!=null) {
                    for (episode in it.episodes) {
                        db.rickMortyDao.insertEpisode(episode)
                    }
                    Log.i("MyRes", "inserting episodes successful")
                } else {Log.i("MyRes", "inserting failed, episodes = null")}
            }, {
                Log.i("MyRes", "loading episodes failed: "+it.message)
            })
        compositeDisposable.add(disposable)
    }

    fun getCharacterById(requiredId: Int): LiveData<Character> {
        return db.rickMortyDao.getCharacterById(requiredId)
    }

    fun getEpisodesByCharacter(requiredCharacterUrl: String): LiveData<List<Episode>> {
        return db.rickMortyDao.getEpisodesByCharacter(requiredCharacterUrl)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}