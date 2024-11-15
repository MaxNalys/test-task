package com.example.testtask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.testtask.data.model.Spell
import com.example.testtask.data.repository.SpellRepository
import kotlinx.coroutines.launch

class SpellViewModel(private val spellRepository: SpellRepository) : ViewModel() {
    val spells: LiveData<List<Spell>> = liveData {
        val data = spellRepository.getSpells()
        emit(data)
    }
}
