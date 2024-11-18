package com.example.testtask.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.testtask.data.model.CharacterModel
import com.example.testtask.data.model.Spell
import com.example.testtask.data.repository.CharacterRepository
import com.example.testtask.utils.SharedPreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterViewModel(private val repository: CharacterRepository, private val sharedPreferencesHelper: SharedPreferencesHelper) : ViewModel() {

    private val _characters: MutableLiveData<List<CharacterModel>> = MutableLiveData()
    val characters: LiveData<List<CharacterModel>> = _characters

    suspend fun getCharacters() = withContext(Dispatchers.IO) {
        val characters = repository.getCharacters()
        _characters.postValue(characters)
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
            if (it.spells == null) {
                it.spells = mutableListOf()
            }

            if (!it.spells.contains(spellName)) {
                it.spells.add(spellName)
                sharedPreferencesHelper.saveCharacters(characters)
            }
        }
    }

}