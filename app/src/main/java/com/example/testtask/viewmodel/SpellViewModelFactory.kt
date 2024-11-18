package com.example.testtask.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testtask.data.repository.SpellRepository

class SpellViewModelFactory(private val spellRepository: SpellRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpellViewModel::class.java)) {
            return SpellViewModel(spellRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}