package com.example.testtask.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtask.R
import com.example.testtask.data.model.House
import com.example.testtask.databinding.FragmentHouseListBinding
import com.example.testtask.ui.adapter.HouseAdapter

class HouseListFragment : Fragment() {
    private var _binding: FragmentHouseListBinding? = null
    private val binding get() = _binding!!

    private lateinit var houseAdapter: HouseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHouseListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHouseRecyclerView()
    }

    private fun setupHouseRecyclerView() {
        val houses = listOf(
            House("Gryffindor", R.drawable.ic_gryffindor),
            House("Slytherin", R.drawable.ic_slytherin),
            House("Hufflepuff", R.drawable.ic_hufflepuff),
            House("Ravenclaw", R.drawable.ic_ravenclaw)
        )
        binding.allHousesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        houseAdapter = HouseAdapter(houses) { houseName -> onHouseClick(houseName) }
        binding.allHousesRecyclerView.adapter = houseAdapter
    }

    private fun onHouseClick(houseName: String) {
        // Navigate to the CharactersByHomeFragment using SafeArgs
        val action = HouseListFragmentDirections
            .actionHouseListFragmentToCharactersByHomeFragment(houseName)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
