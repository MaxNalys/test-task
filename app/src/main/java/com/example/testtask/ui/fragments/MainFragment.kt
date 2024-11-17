package com.example.testtask.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testtask.R

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Якщо стан не збережено, додаємо фрагменти
        if (savedInstanceState == null) {
            val fragmentTransaction = childFragmentManager.beginTransaction()

            // Додаємо фрагменти в контейнери
            fragmentTransaction.replace(R.id.character_container, CharacterListFragment())
            fragmentTransaction.replace(R.id.house_container, HouseListFragment())
            fragmentTransaction.replace(R.id.spell_container, SpellListFragment())

            fragmentTransaction.commit()
        }
    }
}
