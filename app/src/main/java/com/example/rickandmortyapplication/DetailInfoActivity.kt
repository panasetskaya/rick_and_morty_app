package com.example.rickandmortyapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapplication.DATA.MainViewModel
import com.squareup.picasso.Picasso

class DetailInfoActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var textViewName: TextView
    private lateinit var textViewLocation: TextView
    private lateinit var textViewSpecies: TextView
    private lateinit var textViewStatus: TextView
    private lateinit var buttonToEpisodes: Button
    private lateinit var imageViewImage: ImageView
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_info)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        textViewLocation = findViewById(R.id.textViewLocation)
        textViewName = findViewById(R.id.textViewCharacterNameBig)
        textViewStatus = findViewById(R.id.textViewStatus)
        textViewSpecies = findViewById(R.id.textViewSpecies)
        buttonToEpisodes = findViewById(R.id.buttonToEpisodes)
        imageViewImage = findViewById(R.id.imageViewCharacterImageBig)
        val intent = intent
        id = intent.getIntExtra("id", 1)
        id?.let {
            viewModel.getCharacterById(it).observe(this, Observer {
                Picasso.get().load(it.image).into(imageViewImage)
                textViewLocation.text = it.location?.name
                textViewName.text = it.name
                textViewSpecies.text = it.species
                textViewStatus.text = it.status
                val buttonText = getString(R.string.to_episodes)
                "$buttonText ${it.name}".also { buttonToEpisodes.text = it }
            })
        }
        buttonToEpisodes.setOnClickListener {

        }



    }
}