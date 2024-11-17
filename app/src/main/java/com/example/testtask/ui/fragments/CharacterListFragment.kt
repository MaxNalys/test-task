package com.example.testtask.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtask.data.model.CharacterModel
import com.example.testtask.data.remote.ApiClient
import com.example.testtask.data.repository.CharacterRepository
import com.example.testtask.databinding.FragmentCharacterListBinding
import com.example.testtask.ui.adapter.CharacterAdapter
import com.example.testtask.viewmodel.CharacterViewModel
import com.example.testtask.viewmodel.CharacterViewModelFactory

class CharacterListFragment : Fragment() {

    private lateinit var binding: FragmentCharacterListBinding
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var viewModel: CharacterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)

        // Initialize the ViewModel
        val repository = CharacterRepository(ApiClient.apiService)
        val factory = CharacterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CharacterViewModel::class.java)

        // Set up RecyclerView
        setupRecyclerView()

        // Observe characters from the ViewModel
        observeCharacters()

        return binding.root
    }

    private fun setupRecyclerView() {
        // Setup RecyclerView
        binding.allCharactersRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        characterAdapter = CharacterAdapter(emptyList(), ::onCharacterClick)
        binding.allCharactersRecyclerView.adapter = characterAdapter
    }

    private fun observeCharacters() {
        // Observe characters live data and update adapter
        viewModel.characters.observe(viewLifecycleOwner) { characters ->
            characterAdapter.updateData(characters)
        }
    }

    private fun onCharacterClick(character: CharacterModel) {
      
    }

}

