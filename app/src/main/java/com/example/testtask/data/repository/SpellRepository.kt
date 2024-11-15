package com.example.testtask.data.repository

import ApiService
import com.example.testtask.data.model.Spell

class SpellRepository(private val apiService: ApiService) {
    suspend fun getSpells(): List<Spell> {
        val response = apiService.getSpells()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Failed to fetch characters: ${response.code()}")
        }
    }


}