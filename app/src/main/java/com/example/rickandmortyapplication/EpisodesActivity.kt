package com.example.rickandmortyapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapplication.ADAPTERS.EpisodesAdapter
import com.example.rickandmortyapplication.DATA.MainViewModel

class EpisodesActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerViewEpisodes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episodes)
        recyclerViewEpisodes = findViewById(R.id.recyclerEpisodes)
        recyclerViewEpisodes.layoutManager = LinearLayoutManager(this)
        val episodeAdapter = EpisodesAdapter()
        recyclerViewEpisodes.adapter = episodeAdapter
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.loadEpisodes()
        viewModel.episodesList.observe(this, Observer {
            episodeAdapter.clear()
            episodeAdapter.episodesList = it.toMutableList()
            Log.i("MyResult", "episodes adapter list is set and ready")
        })
    }
}