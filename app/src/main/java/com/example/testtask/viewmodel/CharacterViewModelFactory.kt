package com.example.testtask.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testtask.data.repository.CharacterRepository
import com.example.testtask.utils.SharedPreferencesHelper

class CharacterViewModelFactory(
    private val repository: CharacterRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            return CharacterViewModel(repository, sharedPreferencesHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}