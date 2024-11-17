package com.example.testtask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.testtask.data.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {
    val characters = liveData(Dispatchers.IO) {
        emit(repository.getCharacters())
    }

    fun getCharactersByHouse(house: String) = liveData(Dispatchers.IO) {
        val characters = repository.getCharactersByHouse(house)
        emit(characters)
    }
}