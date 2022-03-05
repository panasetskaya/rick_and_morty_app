package com.example.rickandmortyapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapplication.ADAPTERS.CharacterPagingAdapter
import com.example.rickandmortyapplication.ADAPTERS.CharactersAdapter
import com.example.rickandmortyapplication.API.ApiFactory
import com.example.rickandmortyapplication.DATA.MainViewModel
import com.example.rickandmortyapplication.DATA.RickMortyViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerViewCharacters: RecyclerView
    private lateinit var rickMortyViewModel: RickMortyViewModel
    private lateinit var pagingAdapter: CharacterPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerViewCharacters = findViewById(R.id.recyclerCharacters)
        recyclerViewCharacters.layoutManager = LinearLayoutManager(this)
        rickMortyViewModel = ViewModelProvider(this)[RickMortyViewModel::class.java]
        pagingAdapter = CharacterPagingAdapter()
        Log.i("MyRes", "adapter item  count ${pagingAdapter.itemCount}")
        recyclerViewCharacters.adapter = pagingAdapter



        lifecycleScope.launch {
            rickMortyViewModel.getCharacters().distinctUntilChanged().collectLatest {
                Log.i("MyRes", "data submitting")
                pagingAdapter.submitData(it)
                Log.i("MyRes", "data submitted")
            }
        }





//        val adapter = CharactersAdapter()
//        recyclerViewCharacters.adapter = adapter
//        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
//        viewModel.loadCharacters()
//        viewModel.loadEpisodes()
//        viewModel.charactersList.observe(this, Observer { charactersList -> charactersList.let {
//            adapter.clear()
//            adapter.charactersList = it.toMutableList()
//            Log.i("MyResult", "character adapter list is set and ready") }
//        })
//        adapter.onCharacterClick = {
//            val intent = Intent(this,DetailInfoActivity::class.java)
//            intent.putExtra("id", it.id)
//            startActivity(intent)
//        }
}
}
