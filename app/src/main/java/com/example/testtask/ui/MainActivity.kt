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
import com.example.testtask.data.model.Spell
import com.example.testtask.data.repository.SpellRepository
import com.example.testtask.databinding.ActivityMainBinding
import com.example.testtask.ui.adapter.CharacterAdapter
import com.example.testtask.ui.adapter.HouseAdapter
import com.example.testtask.ui.adapter.SpellAdapter
import com.example.testtask.viewmodel.CharacterViewModelFactory
import com.example.testtask.viewmodel.SpellViewModel
import com.example.testtask.viewmodel.SpellViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(CharacterRepository(ApiClient.apiService))
    }

    private val spellViewModel: SpellViewModel by viewModels {
        SpellViewModelFactory(SpellRepository(ApiClient.apiService))
    }

    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var houseAdapter: HouseAdapter
    private lateinit var spellAdapter: SpellAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCharacterRecyclerView()
        setupHouseRecyclerView()
        setupSpellRecyclerView()
        observeCharacters()
        observeSpells()

        binding.moreCharacters.setOnClickListener {
            startActivity(Intent(this, AllCharactersActivity::class.java))
        }

        // Fixing the navigation for spells
        binding.moreSpells.setOnClickListener {
            startActivity(Intent(this, AllSpellsActivity::class.java)) // Navigate to the correct spells activity
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
        houseAdapter = HouseAdapter(houses) { houseName -> onHouseClick(houseName) }
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

    private fun setupSpellRecyclerView() {
        binding.allSpellsRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        spellAdapter = SpellAdapter(emptyList()) { spell ->
            onSpellClick(spell) // Handle the click event for spells
        }
        binding.allSpellsRecyclerView.adapter = spellAdapter
    }

    private fun observeSpells() {
        spellViewModel.spells.observe(this) { spells ->
            spellAdapter.updateData(spells) // Update spell RecyclerView with new data
        }
    }

    private fun onSpellClick(spell: Spell) {
        // Start SpellDetailActivity and pass the selected spell
        Intent(this, SpellDetailActivity::class.java).apply {
            putExtra("spell", spell) // Pass the spell object
        }.also {
            startActivity(it)
        }
    }
}

