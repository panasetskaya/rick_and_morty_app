package com.example.rickandmortyapplication.ADAPTERS

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapplication.POJO.Episode
import com.example.rickandmortyapplication.R

class EpisodePagingAdapter: PagingDataAdapter<Episode, EpisodePagingAdapter.EpisodeViewHolder>(COMPARATOR) {

    class EpisodeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        companion object {
            fun getInstance(parent: ViewGroup): EpisodeViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.episode_item, parent,false)
                return EpisodeViewHolder(view)
            }
        }

        var textViewEpisodeName: TextView = itemView.findViewById(R.id.textViewEpisodeName)
        var textViewEpisodeNumber: TextView = itemView.findViewById(R.id.textViewEpisodeNumber)
        var textViewEpisodeReleaseDate: TextView = itemView.findViewById(R.id.textViewRelease)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        getItem(position)?.let {
            holder.textViewEpisodeName.text = it.name
            holder.textViewEpisodeNumber.text = it.episode
            holder.textViewEpisodeReleaseDate.text = it.airDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder.getInstance(parent)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean =
                oldItem == newItem
        }
    }
}