package com.example.practice

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    interface OnLocationPassListener {
        fun onLocationPass(lat : String, lng : String)
    }
    lateinit var dataPassListener: OnLocationPassListener

    private val callback = OnMapReadyCallback { googleMap ->
        val point = LatLng(37.0, 126.0)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(point))
        googleMap?.setOnMapClickListener { latlng ->
            googleMap.clear()
            var location = LatLng(latlng.latitude, latlng.longitude)
            googleMap?.addMarker(MarkerOptions().position(location).title("Here?"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            dataPassListener.onLocationPass(location.latitude.toString(), location.longitude.toString())
//            Log.d("ITM",location.longitude.toString())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPassListener = context as OnLocationPassListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}