package com.example.rickandmortyapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapplication.API.ApiFactory
import com.example.rickandmortyapplication.DATA.MainViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val disposable = ApiFactory.apiService.getCharacters()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("MyRes", "loading succeful: "+it.characters.toString())
            }, {
                Log.i("MyRes", "loading failed: "+it.message)
            })
        val disposable2 = ApiFactory.apiService.getEpisodes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("MyRes", "loading succeful: "+it.episodes.toString())
            }, {
                Log.i("MyRes", "loading failed: "+it.message)
            })
        compositeDisposable.add(disposable)
        compositeDisposable.add(disposable2)


    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}