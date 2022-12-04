package com.example.practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice.databinding.ActivityLocationBinding

class LocationActivity : AppCompatActivity() {
    val binding by lazy { ActivityLocationBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.mapBtn.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        var locations = mutableListOf<LocInfo>()

        val item1 =LocInfo("Restaurant", "08-22", "It was good!")
        locations.add(item1)

        val locAdapter = LocAdapter(locations)
        binding.locRecView.adapter = locAdapter
        binding.locRecView.layoutManager =LinearLayoutManager(this)
    }
}