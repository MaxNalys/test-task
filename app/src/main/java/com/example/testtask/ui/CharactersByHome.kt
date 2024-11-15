package com.example.testtask.ui

import CharacterModel
import CharacterRepository
import CharacterViewModel
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testtask.ui.adapter.CharacterAdapter
import com.example.testtask.viewmodel.CharacterViewModelFactory
import com.example.testtask.databinding.ActivityAllCharactersBinding
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager

class CharactersByHome : AppCompatActivity() {

    private lateinit var binding: ActivityAllCharactersBinding
    private lateinit var characterAdapter: CharacterAdapter
    private val viewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(CharacterRepository(ApiClient.apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val houseName = intent.getStringExtra("houseName")

        houseName?.let {
            setupRecyclerView()
            observeCharacters(it)
        }
    }
    private fun setupRecyclerView() {
        binding.allCharactersByHome.layoutManager = GridLayoutManager(this, 2)
        characterAdapter = CharacterAdapter(emptyList(), ::onCharacterClick)
        binding.allCharactersByHome.adapter = characterAdapter
    }


    private fun observeCharacters(house: String) {
        viewModel.getCharactersByHouse(house).observe(this) { characters ->
            characterAdapter.updateData(characters)
        }
    }

    private fun onCharacterClick(character: CharacterModel) {
        Intent(this, CharacterDetailActivity::class.java).apply {
            putExtra("character", character)
        }.also {
            startActivity(it)
        }
    }
}
