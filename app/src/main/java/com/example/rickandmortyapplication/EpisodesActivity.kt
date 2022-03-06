package com.example.rickandmortyapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapplication.ADAPTERS.CharactersLoadStateAdapter
import com.example.rickandmortyapplication.ADAPTERS.EpisodePagingAdapter
import com.example.rickandmortyapplication.DATA.RickMortyViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class EpisodesActivity : AppCompatActivity() {

    private lateinit var viewModel: RickMortyViewModel
    private lateinit var recyclerViewEpisodes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episodes)

        recyclerViewEpisodes = findViewById(R.id.recyclerEpisodes)
        recyclerViewEpisodes.layoutManager = LinearLayoutManager(this)
        val episodeAdapter = EpisodePagingAdapter()
        recyclerViewEpisodes.adapter = episodeAdapter.withLoadStateAdapters(
            header = CharactersLoadStateAdapter(episodeAdapter),
            footer = CharactersLoadStateAdapter(episodeAdapter)
        )
        viewModel = ViewModelProvider(this)[RickMortyViewModel::class.java]
        val intent = intent
        val url = intent.getStringExtra("url")
        lifecycleScope.launch {
            url?.let {
                viewModel.getEpisodes(it).distinctUntilChanged().collectLatest {
                    episodeAdapter.submitData(it)
                }
            }
        }
    }
}