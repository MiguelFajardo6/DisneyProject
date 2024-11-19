package com.example.disneyproject.view.adapters

import android.icu.text.SimpleDateFormat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.disneyproject.R
import com.example.disneyproject.data.remote.model.Disney
import com.example.disneyproject.databinding.DisneyElementBinding
import java.util.Locale


class DisneyViewHolder(
    private val binding: DisneyElementBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(disney: Disney) {

        val createdAt = disney.createdAt
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val targetFormat = SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
        val date = originalFormat.parse(createdAt)
        val formattedDate = targetFormat.format(date)


        binding.tvTitle.text = disney.name
        binding.tvDeveloper.text = formattedDate

        Glide.with(binding.root.context)
            .load(disney.imageUrl)
            .error(R.drawable.mickeyimg)
            .placeholder(R.drawable.mickeyimg)
            .into(binding.ivThumbnail)
    }
}