package com.example.rickandmortyapplication.ADAPTERS

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapplication.POJO.Episode
import com.example.rickandmortyapplication.R

class EpisodesAdapter: RecyclerView.Adapter<EpisodesAdapter.EpisodeHolder>() {

    var episodesList = mutableListOf<Episode>()
        set(value) {
            field=value
            notifyDataSetChanged()
        }

    inner class EpisodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewEpisodeName: TextView = itemView.findViewById(R.id.textViewEpisodeName)
        val textViewEpisodeNumber: TextView = itemView.findViewById(R.id.textViewEpisodeNumber)
        val textViewEpisodeReleaseDate: TextView = itemView.findViewById(R.id.textViewRelease)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.episode_item, parent,false)
        return EpisodeHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeHolder, position: Int) {
        val episode = episodesList[position]
        holder.textViewEpisodeName.text = episode.name
        holder.textViewEpisodeNumber.text = episode.episode
        holder.textViewEpisodeReleaseDate.text = episode.airDate
    }

    override fun getItemCount() = episodesList.size

    fun clear() {
        episodesList.clear()
        notifyDataSetChanged()
    }
}