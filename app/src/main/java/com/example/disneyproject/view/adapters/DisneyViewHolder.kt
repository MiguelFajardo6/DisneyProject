package com.example.disneyproject.view.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.disneyproject.R
import com.example.disneyproject.data.remote.model.Disney
import com.example.disneyproject.databinding.DisneyElementBinding

class DisneyViewHolder(
    private val binding: DisneyElementBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(disney: Disney) {
        binding.tvTitle.text = disney.name
        Glide.with(binding.root.context)
            .load(disney.imageUrl)
            //.error(R.drawable.background_game)
            //.placeholder(R.drawable.background_game)
            .into(binding.ivThumbnail)
} }