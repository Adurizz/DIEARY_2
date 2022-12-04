package com.example.practice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practice.databinding.LocViewBinding

class LocAdapter(val locations : MutableList<LocInfo>): RecyclerView.Adapter<LocAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LocViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val locs = locations.get(position)
        holder.bind(locs)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    class ViewHolder(val binding: LocViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(locInfos:LocInfo) {
            binding.locName.text = locInfos.locName
            binding.date.text = locInfos.date
            binding.comment.text = locInfos.comments
        }
    }
}