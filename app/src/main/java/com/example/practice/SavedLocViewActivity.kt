package com.example.practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.practice.databinding.ActivitySavedLocViewBinding

class SavedLocViewActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivitySavedLocViewBinding
    var lat : String? = ""
    var lng : String? = ""
    var name : String? = ""


    init {
        instance = this
    }

    companion object {
        private var instance:SavedLocViewActivity?=null
        fun getInstance():SavedLocViewActivity? {
            return instance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("ITM", "Passed into saved location view activity")
        super.onCreate(savedInstanceState)

        binding = ActivitySavedLocViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lat = intent.getStringExtra("lat")
        lng = intent.getStringExtra("lng")
        name = intent.getStringExtra("name")

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        Log.d("ITM", "${lat}, ${lng}")
        var point = LatLng(0.0,0.0)
        // Add a marker in Sydney and move the camera
        if (lat != null && lng != null) {
            point = LatLng(lat!!.toDouble(), lng!!.toDouble())
        }

        mMap.addMarker(MarkerOptions().position(point).title(name))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point))
    }
}