package com.example.testtask.ui

import CharacterModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.testtask.R
import com.example.testtask.databinding.ActivityCharacterDetailBinding

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val character = intent.getSerializableExtra("character") as? CharacterModel

        character?.let {
            binding.characterDetailName.text = it.name
            binding.characterDetailAlternateNames.text = it.alternate_names?.joinToString(", ")
            binding.characterDetailGender.text = it.gender
            binding.characterDetailDateOfBirth.text = it.dateOfBirth ?: "Unknown"
            binding.characterDetailSpells.text = it.spell ?: "Unknown"
            binding.characterDetailActor.text = it.actor ?: "Unknown"
            binding.characterDetailPatronus.text = it.patronus ?: "Unknown"

            Glide.with(this)
                .load(character.image)
                .into(binding.characterDetailImage)

            character.house?.let { house ->
                when (house) {
                    "Gryffindor" -> binding.characterDetailHouse.setImageResource(R.drawable.ic_gryffindor)
                    "Hufflepuff" -> binding.characterDetailHouse.setImageResource(R.drawable.ic_hufflepuff)
                    "Ravenclaw" -> binding.characterDetailHouse.setImageResource(R.drawable.ic_ravenclaw)
                    "Slytherin" -> binding.characterDetailHouse.setImageResource(R.drawable.ic_slytherin)
                    else -> binding.characterDetailHouse.setImageResource(R.drawable.ic_error)
                }
            }
        }
    }
}
