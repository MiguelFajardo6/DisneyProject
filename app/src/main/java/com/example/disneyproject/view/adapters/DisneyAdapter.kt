package com.example.disneyproject.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.disneyproject.data.remote.model.Disney
import com.example.disneyproject.databinding.DisneyElementBinding

class DisneyAdapter(
    private val disney: List<Disney>,
    private val onDisneyClick: (Disney) -> Unit
): RecyclerView.Adapter<DisneyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisneyViewHolder {
        val binding = DisneyElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DisneyViewHolder(binding)
    }

    override fun getItemCount(): Int = disney.size

    override fun onBindViewHolder(holder: DisneyViewHolder, position: Int) {
        val disne = disney[position]
        holder.bind(disne)
        holder.itemView.setOnClickListener { onDisneyClick(disne) }
    }
}