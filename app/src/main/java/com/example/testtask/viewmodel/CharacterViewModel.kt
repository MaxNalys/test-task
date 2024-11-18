package com.example.testtask.viewmodel


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
}