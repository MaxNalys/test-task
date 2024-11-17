package com.example.testtask.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testtask.data.model.CharacterModel
import com.example.testtask.data.remote.ApiClient
import com.example.testtask.data.repository.CharacterRepository
import com.example.testtask.databinding.FragmentAllCharactersBinding
import com.example.testtask.ui.adapter.CharacterAdapter
import com.example.testtask.viewmodel.CharacterViewModel
import com.example.testtask.viewmodel.CharacterViewModelFactory

class CharactersByHomeFragment : Fragment() {

    private lateinit var binding: FragmentAllCharactersBinding
    private val viewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(CharacterRepository(ApiClient.apiService))
    }
    private lateinit var characterAdapter: CharacterAdapter

    // Отримуємо будинок через аргументи
    private var houseName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Отримуємо будинок з аргументів
        houseName = arguments?.getString("houseName")

        houseName?.let {
            setupRecyclerView()
            observeCharacters(it)
        }
    }

    private fun setupRecyclerView() {
        binding.allCharacters.layoutManager = GridLayoutManager(context, 2)
        characterAdapter = CharacterAdapter(emptyList(), ::onCharacterClick)
        binding.allCharacters.adapter = characterAdapter
    }

    private fun observeCharacters(house: String) {
        viewModel.getCharactersByHouse(house).observe(viewLifecycleOwner) { characters ->
            characterAdapter.updateData(characters)
        }
    }

    private fun onCharacterClick(character: CharacterModel) {
        // Тут можемо передати персонажа в інший фрагмент чи активіті
        val action = CharactersByHomeFragmentDirections
            .actionCharactersByHomeFragmentToCharacterDetailFragment(character)
        // Викликаємо навігацію
        findNavController().navigate(action)
    }
}
