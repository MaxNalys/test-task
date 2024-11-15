package com.example.testtask.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testtask.R
import com.example.testtask.data.model.Spell
import com.example.testtask.databinding.ActivitySpellDetailBinding

class SpellDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpellDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySpellDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spell = intent.getSerializableExtra("spell") as? Spell

        spell?.let {
            binding.spellDetailName.text = it.name
            binding.spellDescription.text = it.description ?: "No description available"

            binding.spellDetailImage.setImageResource(R.drawable.magic_wand)
        }
    }
}
