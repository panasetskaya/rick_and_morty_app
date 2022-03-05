package com.example.rickandmortyapplication.ADAPTERS

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapplication.POJO.Character
import com.example.rickandmortyapplication.R
import com.squareup.picasso.Picasso

class CharacterPagingAdapter: PagingDataAdapter<Character, CharacterPagingAdapter.CharacterViewHolder>(COMPARATOR) {


    var onCharacterClick: ((Character) -> Unit)? = null

    class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


        companion object {
            fun getInstance(parent: ViewGroup): CharacterViewHolder {
                Log.i("MyRes", "CharacterViewHolder.getInstance сработал")
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.character_item, parent,false)
                return CharacterViewHolder(view)
            }
        }

        var textViewCharacterName: TextView = itemView.findViewById(R.id.textViewCharacterName)
        var imageViewCharacterImage: ImageView = itemView.findViewById(R.id.imageViewCharacterImage)

        val thisItemView = itemView
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let {
            holder.textViewCharacterName.text = it.name
            Log.i("MyRes", "character name:"+ it.name)
            Picasso.get().load(it.image).into(holder.imageViewCharacterImage)
            val thisItem = it
            holder.thisItemView.setOnClickListener { onCharacterClick?.invoke(thisItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        Log.i("MyRes", "onCreateViewHolder сработал")
        return CharacterViewHolder.getInstance(parent)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem == newItem
        }
    }
}