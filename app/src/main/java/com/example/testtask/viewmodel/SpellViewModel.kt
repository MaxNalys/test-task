package com.example.testtask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.testtask.data.model.Spell
import com.example.testtask.data.repository.SpellRepository


class SpellViewModel(private val spellRepository: SpellRepository) : ViewModel() {
    val spells: LiveData<List<Spell>> = liveData {
        val data = spellRepository.getSpells()
        emit(data)
    }
}