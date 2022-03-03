package com.example.rickandmortyapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapplication.ADAPTERS.CharactersAdapter
import com.example.rickandmortyapplication.API.ApiFactory
import com.example.rickandmortyapplication.DATA.MainViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerViewCharacters: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerViewCharacters = findViewById(R.id.recyclerCharacters)
        recyclerViewCharacters.layoutManager = LinearLayoutManager(this)
        val adapter = CharactersAdapter()
        recyclerViewCharacters.adapter = adapter
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.loadCharacters()
        viewModel.charactersList.observe(this, Observer { charactersList -> charactersList.let {
            adapter.clear()
            adapter.charactersList = it.toMutableList()
            Log.i("MyResult", "character adapter list is set and ready") }
        })
        adapter.onCharacterClick = {
            val intent = Intent(this,DetailInfoActivity::class.java)
            intent.putExtra("id", it.id)
            startActivity(intent)
        }
    }
}