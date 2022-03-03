package com.example.rickandmortyapplication.ADAPTERS

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapplication.POJO.Character
import com.example.rickandmortyapplication.R
import com.squareup.picasso.Picasso

class CharactersAdapter: RecyclerView.Adapter<CharactersAdapter.CharacterHolder>() {

    var onCharacterClick: ((Character) -> Unit)? = null

    var charactersList = mutableListOf<Character>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    inner class CharacterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewCharacterName: TextView = itemView.findViewById(R.id.textViewCharacterName)
        val imageViewCharacterImage: ImageView = itemView.findViewById(R.id.imageViewCharacterImage)
        init {
            itemView.setOnClickListener { onCharacterClick?.invoke(charactersList[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent,false)
        return CharacterHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        val character = charactersList[position]
        holder.textViewCharacterName.text = character.name
        Picasso.get().load(character.image).into(holder.imageViewCharacterImage)
    }

    override fun getItemCount() = charactersList.size

    fun clear() {
        charactersList.clear()
        notifyDataSetChanged()
    }
}