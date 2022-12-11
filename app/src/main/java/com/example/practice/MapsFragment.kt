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
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    // MapActivity로 위치 정보 전달하기 위한 interface 생성
    interface OnLocationPassListener {
        fun onLocationPass(lat : String, lng : String)
    }
    lateinit var dataPassListener: OnLocationPassListener

    private val callback = OnMapReadyCallback { googleMap ->
        // 초기 위치.. 서울로 설정
        val point = LatLng(37.5518770187, 126.99262798)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(point))
        val cameraPosition = CameraPosition.Builder()
            .target(point)
            .zoom(10.0f)
            .build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        // 위치 고를시
        googleMap?.setOnMapClickListener { latlng ->
            googleMap.clear()
            var location = LatLng(latlng.latitude, latlng.longitude)
            googleMap?.addMarker(MarkerOptions().position(location).title("Here?"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            dataPassListener.onLocationPass(location.latitude.toString(), location.longitude.toString())
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