package com.example.practice

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.practice.databinding.ActivityLocationBinding
import com.example.practice.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity(), MapsFragment.OnLocationPassListener {
    val binding by lazy { ActivityMapBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mapFrangment = MapsFragment()

        val fManager = supportFragmentManager
        val transaction = fManager.beginTransaction()

        transaction.add(binding.mapFrame.id, mapFrangment)
        transaction.commit()

        binding.savBtn.setOnClickListener {
            if (binding.latitudeText.text == "") {
                Toast.makeText(this,"please select location!", Toast.LENGTH_SHORT).show();
            }
            else {
                val intent = Intent(this, LocationActivity::class.java).apply {
                    putExtra("title", binding.mapTitle.text.toString())
                    putExtra("date", binding.mapDate.text.toString())
                    putExtra("content",binding.mapContent.text.toString())
                    putExtra("latitude",binding.latitudeText.text.toString())
                    putExtra("longitude", binding.longtitudeText.text.toString())
                }
                setResult(RESULT_OK,intent)
                finish()
            }
        }
    }

    override fun onLocationPass(lat: String, lng: String) {
        binding.latitudeText.text = lat
        binding.longtitudeText.text = lng
        Log.d("ITM", lat)
        Log.d("ITM", lng)
    }
}