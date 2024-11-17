package com.example.testtask.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtask.data.model.Spell
import com.example.testtask.data.remote.ApiClient
import com.example.testtask.data.repository.SpellRepository
import com.example.testtask.databinding.FragmentSpellListBinding
import com.example.testtask.ui.adapter.SpellAdapter
import com.example.testtask.viewmodel.SpellViewModel
import com.example.testtask.viewmodel.SpellViewModelFactory

class SpellListFragment : Fragment() {

    private lateinit var binding: FragmentSpellListBinding
    private lateinit var spellAdapter: SpellAdapter
    private lateinit var viewModel: SpellViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSpellListBinding.inflate(inflater, container, false)

        // Initialize the ViewModel
        val repository = SpellRepository(ApiClient.apiService)
        val factory = SpellViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(SpellViewModel::class.java)

        // Set up RecyclerView
        setupRecyclerView()

        // Observe spells from the ViewModel
        observeSpells()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.allSpellsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        spellAdapter = SpellAdapter(emptyList()) { spell ->
            onSpellClick(spell)
        }
        binding.allSpellsRecyclerView.adapter = spellAdapter
    }

    private fun observeSpells() {
        viewModel.spells.observe(viewLifecycleOwner) { spells ->
            spellAdapter.updateData(spells)
        }
    }

    private fun onSpellClick(spell: Spell) {
        // Handle spell click
    }
}
