package com.example.testtask.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtask.R
import com.example.testtask.data.model.CharacterModel
import com.example.testtask.data.remote.ApiClient
import com.example.testtask.data.repository.CharacterRepository
import com.example.testtask.databinding.FragmentAllCharactersBinding
import com.example.testtask.ui.adapter.CharacterAdapter
import com.example.testtask.viewmodel.CharacterViewModel
import com.example.testtask.viewmodel.CharacterViewModelFactory

class AllCharactersFragment : Fragment() {

    private lateinit var binding: FragmentAllCharactersBinding
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var viewModel: CharacterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllCharactersBinding.inflate(inflater, container, false)

        val repository = CharacterRepository(ApiClient.apiService)
        val factory = CharacterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CharacterViewModel::class.java)

        setupRecyclerView()
        observeCharacters()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.allCharacters.layoutManager = GridLayoutManager(context, 2)
        characterAdapter = CharacterAdapter(emptyList(), ::onCharacterClick)
        binding.allCharacters.adapter = characterAdapter
    }

    private fun observeCharacters() {
        viewModel.characters.observe(viewLifecycleOwner) { characters ->
            characterAdapter.updateData(characters)
        }
    }

    private fun onCharacterClick(character: CharacterModel) {
        // Передача даних через Bundle
        val action = AllCharactersFragmentDirections.actionAllCharactersFragmentToCharacterDetailFragment(character)
        findNavController().navigate(action)
    }
}
