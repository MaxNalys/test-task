package com.example.testtask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtask.data.model.CharacterModel
import com.example.testtask.databinding.ItemCharacterBinding

class CharacterAdapter(
    private var characters: List<CharacterModel>,
    private val onCharacterClick: (CharacterModel) -> Unit
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
            binding.characterName.text = character.name

            Glide.with(binding.root.context)
                .load(character.image)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .into(binding.characterImage)

            binding.root.setOnClickListener {
                onCharacterClick(character)
            }
        }
    }
}
