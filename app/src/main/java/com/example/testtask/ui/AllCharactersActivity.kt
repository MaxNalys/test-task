package com.example.testtask.ui


import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testtask.data.model.CharacterModel
import com.example.testtask.data.remote.ApiClient
import com.example.testtask.data.repository.CharacterRepository
import com.example.testtask.databinding.ActivityAllCharactersBinding
import com.example.testtask.ui.adapter.CharacterAdapter
import com.example.testtask.utils.SharedPreferencesHelper
import com.example.testtask.viewmodel.CharacterViewModel
import com.example.testtask.viewmodel.CharacterViewModelFactory


class AllCharactersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllCharactersBinding
    private lateinit var characterAdapter: CharacterAdapter

    private val viewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(
            CharacterRepository(ApiClient.apiService, SharedPreferencesHelper(this)),
            SharedPreferencesHelper(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        binding.allCharactersByHome.layoutManager = GridLayoutManager(this, 2)
        characterAdapter = CharacterAdapter(emptyList(), ::onCharacterClick)
        binding.allCharactersByHome.adapter = characterAdapter
    }

    private fun observeViewModel() {
        viewModel.characters.observe(this) { characters ->
            characterAdapter.updateData(characters)
        }
    }

    private fun onCharacterClick(character: CharacterModel) {
        val intent = Intent(this, CharacterDetailActivity::class.java).apply {
            putExtra("character", character)
        }
        startActivity(intent)
    }
}
