package com.example.rickandmortyapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapplication.DATA.RickMortyViewModel
import com.example.rickandmortyapplication.POJO.Character
import com.squareup.picasso.Picasso

class DetailInfoActivity : AppCompatActivity() {

    private lateinit var rickMortyViewModel: RickMortyViewModel
    private lateinit var textViewName: TextView
    private lateinit var textViewLocation: TextView
    private lateinit var textViewSpecies: TextView
    private lateinit var textViewStatus: TextView
    private lateinit var buttonToEpisodes: Button
    private lateinit var imageViewImage: ImageView
    private var id: Int? = null
    private lateinit var character: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_info)

        rickMortyViewModel = ViewModelProvider(this)[RickMortyViewModel::class.java]
        textViewLocation = findViewById(R.id.textViewLocation)
        textViewName = findViewById(R.id.textViewCharacterNameBig)
        textViewStatus = findViewById(R.id.textViewStatus)
        textViewSpecies = findViewById(R.id.textViewSpecies)
        buttonToEpisodes = findViewById(R.id.buttonToEpisodes)
        imageViewImage = findViewById(R.id.imageViewCharacterImageBig)
        val intent = intent
        id = intent.getIntExtra("id", 1)
        id?.let {
            rickMortyViewModel.getCharacterById(it).observe(this, Observer {
                Picasso.get().load(it.image).into(imageViewImage)
                character = it
                textViewLocation.text = it.location?.name
                textViewName.text = it.name
                textViewSpecies.text = it.species
                textViewStatus.text = it.status
                val buttonText = getString(R.string.to_episodes)
                "$buttonText ${it.name}".also { buttonToEpisodes.text = it }
            })
        }
        buttonToEpisodes.setOnClickListener {
            val intent2 = Intent(this, EpisodesActivity::class.java)
            intent2.putExtra("url", character.url)
            startActivity(intent2)
        }



    }
}