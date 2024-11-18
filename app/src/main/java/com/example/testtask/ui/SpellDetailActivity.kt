package com.example.testtask.ui

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.testtask.R
import com.example.testtask.data.model.Spell
import com.example.testtask.databinding.ActivitySpellDetailBinding
import com.example.testtask.viewmodel.CharacterViewModel
import com.example.testtask.viewmodel.CharacterViewModelFactory
import com.example.testtask.data.repository.CharacterRepository
import com.example.testtask.data.remote.ApiClient
import com.example.testtask.utils.SharedPreferencesHelper
import kotlinx.coroutines.launch

class SpellDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpellDetailBinding
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    private val viewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(
            CharacterRepository(ApiClient.apiService, sharedPreferencesHelper),
            sharedPreferencesHelper
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySpellDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesHelper = SharedPreferencesHelper(this)

        val spell = intent.getSerializableExtra("spell") as? Spell

        spell?.let {
            binding.spellDetailName.text = it.name
            binding.spellDescription.text = it.description ?: "No description available"
            binding.spellDetailImage.setImageResource(R.drawable.magic_wand)

            viewModel.characters.observe(this) { characters ->
                val characterNames = characters.map { it.name }
                val spinnerAdapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    characterNames
                ).apply {
                    setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
                binding.characterSpinner.adapter = spinnerAdapter
            }

            binding.learnSpellButton.setOnClickListener {
                val selectedCharacterName = binding.characterSpinner.selectedItem.toString()
                val selectedSpellName = spell.name
                if (selectedCharacterName.isNotEmpty() && selectedSpellName.isNotEmpty()) {
                    lifecycleScope.launch {
                        try {
                            viewModel.assignSpellToCharacterByName(
                                selectedCharacterName,
                                selectedSpellName
                            )

                            val updatedCharacters = sharedPreferencesHelper.getCharacters()
                            val updatedCharacterNames = updatedCharacters.map { it.name }
                            val adapter = ArrayAdapter(
                                this@SpellDetailActivity,
                                android.R.layout.simple_spinner_item,
                                updatedCharacterNames
                            )
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            binding.characterSpinner.adapter = adapter

                            Toast.makeText(
                                this@SpellDetailActivity,
                                "Spell added successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                        } catch (e: Exception) {
                            Log.e("SpellDetailActivity", "Error assigning spell", e)
                            Toast.makeText(
                                this@SpellDetailActivity,
                                "Failed to assign spell",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Please select a character and a spell.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            try {
                viewModel.getCharacters()
            } catch (e: Exception) {
                Log.e("SpellDetailActivity", "Error fetching characters", e)
            }
        }
    }
}
