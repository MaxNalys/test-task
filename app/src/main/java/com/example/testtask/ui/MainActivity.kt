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
import com.example.testtask.databinding.ActivityMainBinding
import com.example.testtask.ui.adapter.CharacterAdapter
import com.example.testtask.viewmodel.CharacterViewModelFactory
import com.example.testtask.ui.CharacterDetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(CharacterRepository(ApiClient.apiService))
    }
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeCharacters()
    }

    private fun setupRecyclerView() {
        // Установлено горизонтальне прокручування
        binding.allCharactersRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Додано SnapHelper для плавного прокручування
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.allCharactersRecyclerView)

        // Ініціалізація адаптера
        characterAdapter = CharacterAdapter(emptyList(), ::onCharacterClick) // передаємо callback
        binding.allCharactersRecyclerView.adapter = characterAdapter
    }

    private fun observeCharacters() {
        viewModel.characters.observe(this) { characters ->
            // Оновлення даних в адаптері
            characterAdapter.updateData(characters)
        }
    }

    // Обробник кліку по персонажу
    private fun onCharacterClick(character: CharacterModel) {
        // Створення Intent для переходу в деталі персонажа
        val intent = Intent(this, CharacterDetailActivity::class.java).apply {
            putExtra("character", character)
        }
        startActivity(intent)
    }
}
