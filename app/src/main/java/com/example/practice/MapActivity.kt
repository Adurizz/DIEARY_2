package com.example.practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practice.databinding.ActivityLocationBinding
import com.example.practice.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {
    val binding by lazy { ActivityMapBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mapFrangment = MapsFragment()

        val fManager = supportFragmentManager
        val transaction = fManager.beginTransaction()

        transaction.add(binding.mapFrame.id, mapFrangment)
        transaction.commit()
    }
//    , MapsFragment.OnLocationPassListener
//    override fun onLocationPass(lat: String?, lng: String?) {
//        binding.latitudeText.text = lat
//        binding.latitudeText.text = lng
//    }
}