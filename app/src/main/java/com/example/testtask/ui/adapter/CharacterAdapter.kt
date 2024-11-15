package com.example.testtask.ui.adapter

import CharacterModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtask.databinding.ItemCharacterBinding

class CharacterAdapter(
    private var characters: List<CharacterModel>,
    private val onCharacterClick: (CharacterModel) -> Unit // Callback для обробки кліку
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int = characters.size

    fun updateData(newCharacters: List<CharacterModel>) {
        characters = newCharacters
        notifyDataSetChanged()
    }

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharacterModel) {
            // Заповнення даних в елементи
            binding.characterName.text = character.name

            // Завантажуємо зображення за допомогою Glide
            Glide.with(binding.root.context)
                .load(character.image) // Завантажуємо зображення з URL
                .placeholder(android.R.drawable.progress_indeterminate_horizontal) // Показуємо індикатор завантаження
                .error(android.R.drawable.stat_notify_error) // Показуємо індикатор помилки, якщо зображення не завантажилось
                .into(binding.characterImage) // Встановлюємо зображення у ImageView

            // Задаємо клікабельність елемента
            binding.root.setOnClickListener {
                onCharacterClick(character) // викликаємо callback
            }
        }
    }
}
