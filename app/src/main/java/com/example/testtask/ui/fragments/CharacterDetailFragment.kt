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

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val character = arguments?.getSerializable("character") as? CharacterModel
        character?.let {
            binding.characterDetailName.text = it.name
            binding.characterDetailAlternateNames.text = it.alternate_names?.joinToString(", ")
            binding.characterDetailGender.text = it.gender
            binding.characterDetailDateOfBirth.text = it.dateOfBirth ?: "Unknown"
            binding.characterDetailSpells.text = it.spell ?: "Unknown"
            binding.characterDetailActor.text = it.actor ?: "Unknown"

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
