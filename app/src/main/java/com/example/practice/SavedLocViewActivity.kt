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
import com.google.android.gms.maps.model.CameraPosition

// 저장된 게시글 선택하면 해당 위치 구글맵에 표기해주는 Activity..
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

        // 게시글에서 위도 경도 받아옴
        lat = intent.getStringExtra("lat")
        lng = intent.getStringExtra("lng")
        name = intent.getStringExtra("name")

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //보여줄 장소 초기화
        var point = LatLng(0.0,0.0)

        // 받아온 위도 경도 할당
        if (lat != null && lng != null) {
            point = LatLng(lat!!.toDouble(), lng!!.toDouble())
        }

        // 지도에 마커 표시
        mMap.addMarker(MarkerOptions().position(point).title(name))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point))
        val cameraPosition = CameraPosition.Builder()
            .target(point)
            .zoom(17.0f)
            .build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}