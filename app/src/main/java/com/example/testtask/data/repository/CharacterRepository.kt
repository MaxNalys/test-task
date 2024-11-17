package com.example.testtask.data.repository

import com.example.testtask.data.model.CharacterModel
import com.example.testtask.data.remote.ApiService

class CharacterRepository(private val apiService: ApiService) {
    suspend fun getCharacters(): List<CharacterModel> {
        val response = apiService.getCharacters()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Failed to fetch characters: ${response.code()}")
        }
    }

    suspend fun getCharactersByHouse(house: String): List<CharacterModel> {
        return apiService.getCharactersByHouse(house)
    }
}
