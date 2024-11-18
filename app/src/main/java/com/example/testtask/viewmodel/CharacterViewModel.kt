package com.example.testtask.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.testtask.data.model.CharacterModel
import com.example.testtask.data.model.Spell
import com.example.testtask.data.repository.CharacterRepository
import com.example.testtask.utils.SharedPreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterViewModel(private val repository: CharacterRepository, private val sharedPreferencesHelper: SharedPreferencesHelper) : ViewModel() {

    val characters = liveData(Dispatchers.IO) {
        emit(repository.getCharacters())
    }



    fun getCharactersByHouse(house: String) = liveData(Dispatchers.IO) {
        val characters = repository.getCharactersByHouse(house)
        emit(characters)
    }
    suspend fun assignRandomSpellsToCharacters(spells: List<Spell>): List<CharacterModel> {
        return withContext(Dispatchers.IO) {
            repository.assignRandomSpellsToCharacters(spells)
        }
    }
    fun assignSpellToCharacterByName(characterName: String, spellName: String) {
        val characters = sharedPreferencesHelper.getCharacters().toMutableList()
        val character = characters.find { it.name == characterName }
        character?.let {
            Log.d("CharacterViewModel", "Character found: ${it.name}")

            if (it.spells == null) {
                it.spells = mutableListOf()
                Log.d("CharacterViewModel", "Initialized spells for character ${it.name}")
            }

            if (!it.spells.contains(spellName)) {
                it.spells.add(spellName)
                sharedPreferencesHelper.saveCharacters(characters) // Зберігаємо зміни в SharedPreferences
                Log.d("CharacterViewModel", "Spell $spellName added to character ${it.name}")
            } else {
                Log.d("CharacterViewModel", "Spell $spellName already exists for character ${it.name}")
            }
        } ?: Log.d("CharacterViewModel", "Character $characterName not found")
    }

}