package com.example.testtask.ui


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.testtask.R
import com.example.testtask.data.model.CharacterModel
import com.example.testtask.data.model.House
import com.example.testtask.data.model.Spell
import com.example.testtask.data.remote.ApiClient
import com.example.testtask.data.repository.CharacterRepository
import com.example.testtask.data.repository.SpellRepository
import com.example.testtask.databinding.ActivityMainBinding
import com.example.testtask.ui.adapter.CharacterAdapter
import com.example.testtask.ui.adapter.HouseAdapter
import com.example.testtask.ui.adapter.SpellAdapter
import com.example.testtask.utils.SharedPreferencesHelper
import com.example.testtask.viewmodel.CharacterViewModel
import com.example.testtask.viewmodel.CharacterViewModelFactory
import com.example.testtask.viewmodel.SpellViewModel
import com.example.testtask.viewmodel.SpellViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper // Ініціалізація пізніше

    private val viewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(
            CharacterRepository(ApiClient.apiService, sharedPreferencesHelper),
            sharedPreferencesHelper
        )
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

        sharedPreferencesHelper = SharedPreferencesHelper(this)

        setupCharacterRecyclerView()
        setupHouseRecyclerView()
        setupSpellRecyclerView()

        observeCharacters()
        observeSpells()

        binding.moreCharacters.setOnClickListener {
            startActivity(Intent(this, AllCharactersActivity::class.java))
        }

        binding.moreSpells.setOnClickListener {
            startActivity(Intent(this, AllSpellsActivity::class.java))
        }

        binding.teachSpellButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val spells = spellViewModel.spells.value ?: emptyList()
                    if (spells.isNotEmpty()) {
                        val updatedCharacters = viewModel.assignRandomSpellsToCharacters(spells)

                        val charactersWithTooManySpells =
                            updatedCharacters.filter { it.spells.size > 3 }

                        if (charactersWithTooManySpells.isNotEmpty()) {
                            Toast.makeText(
                                this@MainActivity, "\n" +
                                        "Maximum number of spells!", Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            characterAdapter.updateData(updatedCharacters)
                            Toast.makeText(
                                this@MainActivity,
                                "You have successfully assigned spells to characters!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "No spells available", Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {
                    Log.e("TeachSpellButton", "Failed to assign spells", e)
                    Toast.makeText(
                        this@MainActivity,
                        "Failed to assign spells: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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
            onSpellClick(spell)
        }
        binding.allSpellsRecyclerView.adapter = spellAdapter
    }

    private fun observeSpells() {
        spellViewModel.spells.observe(this) { spells ->
            spellAdapter.updateData(spells)
        }
    }

    private fun onSpellClick(spell: Spell) {
        Intent(this, SpellDetailActivity::class.java).apply {
            putExtra("spell", spell)
        }.also {
            startActivity(it)
        }
    }
}