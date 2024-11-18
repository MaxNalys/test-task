package com.example.testtask.data.repository

import com.example.testtask.data.model.CharacterModel
import com.example.testtask.data.model.Spell
import com.example.testtask.data.remote.ApiService
import com.example.testtask.utils.SharedPreferencesHelper

class CharacterRepository(
    private val apiService: ApiService,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) {

    suspend fun getCharacters(): List<CharacterModel> {
        val cachedCharacters = sharedPreferencesHelper.getCharacters()
        if (cachedCharacters.isNotEmpty()) {
            return cachedCharacters
        }
        val response = apiService.getCharacters()
        if (response.isSuccessful) {
            val characters = response.body() ?: emptyList()
            sharedPreferencesHelper.saveCharacters(characters)  // Зберігаємо в SharedPreferences
            return characters
        } else {
            throw Exception("Failed to fetch characters: ${response.code()}")
        }
    }

    suspend fun getCharactersByHouse(house: String): List<CharacterModel> {
        return apiService.getCharactersByHouse(house)
    }

    suspend fun assignRandomSpellsToCharacters(spells: List<Spell>): List<CharacterModel> {
        val characters = sharedPreferencesHelper.getCharacters().toMutableList()

        characters.forEach { character ->
            if (character.spells == null) {
                character.spells = mutableListOf()
            }
            val randomSpell = spells.randomOrNull()
            randomSpell?.let {
                if (!character.spells.contains(it.name)) {
                    character.spells.add(it.name)
                }
            }
        }
        sharedPreferencesHelper.saveCharacters(characters)
        return characters
    }
}