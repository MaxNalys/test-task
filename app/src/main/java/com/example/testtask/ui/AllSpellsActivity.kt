package com.example.testtask.ui


import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testtask.data.model.Spell
import com.example.testtask.data.repository.SpellRepository
import com.example.testtask.databinding.ActivityAllCharactersBinding
import com.example.testtask.ui.adapter.SpellAdapter
import com.example.testtask.viewmodel.SpellViewModel
import com.example.testtask.viewmodel.SpellViewModelFactory

class AllSpellsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllCharactersBinding
    private val spellViewModel: SpellViewModel by viewModels {
        SpellViewModelFactory(SpellRepository(ApiClient.apiService))
    }
    private lateinit var spellAdapter: SpellAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeSpells()
    }

    private fun setupRecyclerView() {
        binding.allCharactersByHome.layoutManager = GridLayoutManager(this, 2)
        spellAdapter = SpellAdapter(emptyList(), ::onSpellClick)
        binding.allCharactersByHome.adapter = spellAdapter
    }

    private fun observeSpells() {
        spellViewModel.spells.observe(this) { spells ->
            spellAdapter.updateData(spells)
        }
    }

    private fun onSpellClick(spell: Spell) {
        Intent(this, SpellDetailActivity::class.java).apply {
            putExtra("spell", spell)
        }.also {
            startActivity(it)
        }
    }
}
