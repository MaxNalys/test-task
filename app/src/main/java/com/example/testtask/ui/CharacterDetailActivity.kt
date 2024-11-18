package com.example.testtask.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.testtask.R
import com.example.testtask.data.model.CharacterModel
import com.example.testtask.data.model.Spell  // Не забувайте імпортувати Spell
import com.example.testtask.databinding.ActivityCharacterDetailBinding

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Отримуємо персонажа і спелл з Intent
        val character = intent.getSerializableExtra("character") as? CharacterModel
        val spellName = intent.getStringExtra("spell")  // Отримуємо спелл

        character?.let {
            // Виводимо дані персонажа
            binding.characterDetailName.text = it.name
            binding.characterDetailAlternateNames.text = it.alternate_names?.joinToString(", ")
            binding.characterDetailGender.text = it.gender
            binding.characterDetailDateOfBirth.text = it.dateOfBirth ?: "Unknown"

            // Виводимо список спеллів або повідомлення про відсутність
            if (it.spells.isNullOrEmpty()) {
                binding.characterDetailSpells.text = "No spells assigned"
            } else {
                binding.characterDetailSpells.text = it.spells.joinToString("\n")
            }

            // Якщо є вибраний спелл, виводимо його
            if (!spellName.isNullOrEmpty()) {
                binding.characterDetailSpells.append("\n\nSelected Spell: $spellName")
            }

            binding.characterDetailActor.text = it.actor ?: "Unknown"
            binding.characterDetailPatronus.text = it.patronus ?: "Unknown"

            // Завантажуємо зображення персонажа за допомогою Glide
            Glide.with(this)
                .load(it.image)
                .into(binding.characterDetailImage)

            // Виводимо емблему дому персонажа
            it.house?.let { house ->
                when (house) {
                    "Gryffindor" -> binding.characterDetailHouse.setImageResource(R.drawable.ic_gryffindor)
                    "Hufflepuff" -> binding.characterDetailHouse.setImageResource(R.drawable.ic_hufflepuff)
                    "Ravenclaw" -> binding.characterDetailHouse.setImageResource(R.drawable.ic_ravenclaw)
                    "Slytherin" -> binding.characterDetailHouse.setImageResource(R.drawable.ic_slytherin)
                    else -> binding.characterDetailHouse.setImageResource(R.drawable.ic_error)
                }
            }
        }
    }
}
