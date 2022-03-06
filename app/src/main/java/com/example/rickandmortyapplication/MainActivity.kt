package com.example.rickandmortyapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapplication.ADAPTERS.CharacterPagingAdapter
import com.example.rickandmortyapplication.ADAPTERS.CharactersLoadStateAdapter
import com.example.rickandmortyapplication.DATA.RickMortyViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withLoadStateAdapters(
    header: LoadStateAdapter<*>,
    footer: LoadStateAdapter<*>
): ConcatAdapter {
    addLoadStateListener { loadStates ->
        header.loadState = loadStates.refresh
        footer.loadState = loadStates.append
    }

    return ConcatAdapter(header, this, footer)
}

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewCharacters: RecyclerView
    private lateinit var rickMortyViewModel: RickMortyViewModel
    private lateinit var pagingAdapter: CharacterPagingAdapter
    private lateinit var loadStateAdapter: CharactersLoadStateAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewCharacters = findViewById(R.id.recyclerCharacters)
        recyclerViewCharacters.layoutManager = LinearLayoutManager(this)
        rickMortyViewModel = ViewModelProvider(this)[RickMortyViewModel::class.java]
        pagingAdapter = CharacterPagingAdapter()
        recyclerViewCharacters.adapter = pagingAdapter.withLoadStateAdapters(
            header = CharactersLoadStateAdapter(pagingAdapter),
            footer = CharactersLoadStateAdapter(pagingAdapter))

        pagingAdapter.onCharacterClick = {
            val intent = Intent(this,DetailInfoActivity::class.java)
            intent.putExtra("id", it.id)
            startActivity(intent)
        }

        lifecycleScope.launch {
            rickMortyViewModel.getCharacters().distinctUntilChanged().collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }
}
