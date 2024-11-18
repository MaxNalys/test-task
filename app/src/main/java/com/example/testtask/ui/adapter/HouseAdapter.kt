package com.example.testtask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtask.data.model.House
import com.example.testtask.databinding.ItemHousesBinding

class HouseAdapter(
    private val houses: List<House>,
    private val onHouseClick: (String) -> Unit
) : RecyclerView.Adapter<HouseAdapter.HouseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        val binding = ItemHousesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HouseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {

        val house = houses[position]
        holder.bind(house)
    }

    override fun getItemCount(): Int = houses.size

    inner class HouseViewHolder(private val binding: ItemHousesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(house: House) {
            binding.HousesName.text = house.name
            Glide.with(binding.root.context)
                .load(house.imageResId)
                .into(binding.HousesImage)

            binding.root.setOnClickListener {
                onHouseClick(house.name)
            }
        }
    }
}
