package com.example.testtask.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.testtask.R
import com.example.testtask.data.model.CharacterModel
import com.example.testtask.databinding.FragmentCharacterDetailBinding

class CharacterDetailFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)

        // Отримання об'єкта CharacterModel
        val character = arguments?.getSerializable("character") as? CharacterModel

        // Відображення даних
        character?.let { setupCharacterDetails(it) }

        return binding.root
    }

    private fun setupCharacterDetails(character: CharacterModel) {
        binding.characterDetailName.text = character.name
        binding.characterDetailAlternateNames.text = character.alternate_names?.joinToString(", ")
        binding.characterDetailGender.text = character.gender
        binding.characterDetailDateOfBirth.text = character.dateOfBirth ?: "Unknown"
        binding.characterDetailSpells.text = character.spell ?: "Unknown"
        binding.characterDetailActor.text = character.actor ?: "Unknown"
        binding.characterDetailPatronus.text = character.patronus ?: "Unknown"

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


