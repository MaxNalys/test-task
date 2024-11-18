package com.example.testtask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.R
import com.example.testtask.data.model.Spell
import com.example.testtask.databinding.ItemSpellsBinding

class SpellAdapter(
    private var spells: List<Spell>,
    private val onSpellClick: (Spell) -> Unit
) : RecyclerView.Adapter<SpellAdapter.SpellViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpellViewHolder {
        val binding = ItemSpellsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpellViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpellViewHolder, position: Int) {
        val spell = spells[position]
        holder.bind(spell)
    }

    override fun getItemCount(): Int = spells.size

    fun updateData(newSpells: List<Spell>) {
        spells = newSpells
        notifyDataSetChanged()
    }

    inner class SpellViewHolder(private val binding: ItemSpellsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(spell: Spell) {
            binding.spellName.text = spell.name
            binding.spellImage.setImageResource(R.drawable.magic_wand)
            binding.root.setOnClickListener { onSpellClick(spell) }
        }
    }
}
