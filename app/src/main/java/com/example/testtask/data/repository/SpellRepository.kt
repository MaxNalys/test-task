package com.example.testtask.data.repository

import com.example.testtask.data.model.Spell
import com.example.testtask.data.remote.ApiService

class SpellRepository(private val apiService: ApiService) {
    suspend fun getSpells(): List<Spell> {
        val response = apiService.getSpells()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Failed to fetch spells: ${response.code()}")
        }
    }
}