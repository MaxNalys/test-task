package com.example.testtask.ui

import CharacterModel
import CharacterRepository
import CharacterViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.testtask.R
import com.example.testtask.data.model.House
import com.example.testtask.databinding.ActivityMainBinding
import com.example.testtask.ui.adapter.CharacterAdapter
import com.example.testtask.ui.adapter.HouseAdapter
import com.example.testtask.viewmodel.CharacterViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(CharacterRepository(ApiClient.apiService))
    }
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var houseAdapter: HouseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCharacterRecyclerView()
        setupHouseRecyclerView()
        observeCharacters()

        binding.moreCharacters.setOnClickListener {
            startActivity(Intent(this, AllCharactersActivity::class.java))
        }
    }

    private fun setupCharacterRecyclerView() {
        binding.allCharactersRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        LinearSnapHelper().attachToRecyclerView(binding.allCharactersRecyclerView)
        characterAdapter = CharacterAdapter(emptyList(), ::onCharacterClick)
        binding.allCharactersRecyclerView.adapter = characterAdapter
    }

    private fun setupHouseRecyclerView() {
        val houses = listOf(
            House("Gryffindor", R.drawable.ic_gryffindor),
            House("Slytherin", R.drawable.ic_slytherin),
            House("Hufflepuff", R.drawable.ic_hufflepuff),
            House("Ravenclaw", R.drawable.ic_ravenclaw)
        )
        binding.allHousesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        houseAdapter = HouseAdapter(houses) { houseName ->
            onHouseClick(houseName)
        }
        binding.allHousesRecyclerView.adapter = houseAdapter
    }


    private fun observeCharacters() {
        viewModel.characters.observe(this) { characters ->
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
    private fun onHouseClick(houseName: String) {
        val intent = Intent(this, CharactersByHome::class.java).apply {
            putExtra("houseName", houseName)
        }
        startActivity(intent)
    }
}
