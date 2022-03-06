package com.example.rickandmortyapplication.ADAPTERS

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapplication.R
import com.example.rickandmortyapplication.databinding.LoadingStateItemBinding

class CharactersLoadStateAdapter(private val adapter: PagingDataAdapter<*,*>):
    LoadStateAdapter<CharactersLoadStateAdapter.CharactersLoadStateHolder>() {

      class CharactersLoadStateHolder(
          private val binding: LoadingStateItemBinding,
          private val retryCallback: () -> Unit
      ): RecyclerView.ViewHolder(binding.root) {
          init {
              binding.buttonRetry.setOnClickListener { retryCallback() }
          }

          fun bind(loadState: LoadState) {
              with(binding) {
                  progressbar.isVisible = loadState is LoadState.Loading
                  buttonRetry.isVisible = loadState is LoadState.Error
                  textViewError.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                  textViewError.text = "Не получилось загрузить"
              }
          }
      }

    override fun onBindViewHolder(holder: CharactersLoadStateHolder, loadState: LoadState) = holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): CharactersLoadStateHolder =
        CharactersLoadStateHolder(LoadingStateItemBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.loading_state_item, parent,false))) { adapter.retry()}
}