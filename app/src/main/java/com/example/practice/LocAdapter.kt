package com.example.practice

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
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

            itemView.setOnClickListener {
                try {
                    Intent(LocationActivity.getInstance(), SavedLocViewActivity::class.java).apply {
                        putExtra("lat", locInfos.lat)
                        putExtra("lng", locInfos.lng)
                        putExtra("name", locInfos.locName)
                        Log.d("ITM", "Extras : ${locInfos.lat}, ${locInfos.lng}")
                    }.run { LocationActivity.getInstance()?.startActivity(this) }
                }
                catch (e: java.lang.NullPointerException) {
                    Toast.makeText(MainActivity.getInstance(),"To see the location on Google Maps, plz move to locations page!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}