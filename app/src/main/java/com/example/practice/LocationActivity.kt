package com.example.practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice.databinding.ActivityLocationBinding
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

class LocationActivity : AppCompatActivity() {
    val binding by lazy { ActivityLocationBinding.inflate(layoutInflater)}

    init {
        instance = this
    }

    companion object {
        private var instance:LocationActivity?=null
        fun getInstance():LocationActivity? {
            return instance
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = Firebase.firestore

        var locations = mutableListOf<LocInfo>()
        locations.clear()

        val locAdapter = LocAdapter(locations)
        binding.locRecView.adapter = locAdapter
        binding.locRecView.layoutManager =LinearLayoutManager(this)

        //유저가 올린 게시글 가져옴
        db.collection("locations").document(MainActivity.userId).collection("infos")
            .whereEqualTo("userId", MainActivity.userId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    //Log.d("ITM", "${document.id} => ${document.data.get("locName")}")
                    locations.add(LocInfo(document.data.get("locName").toString(),document.data.get("date").toString(), document.data.get("comments").toString(),document.data.get("lat").toString(), document.data.get("lng").toString()))
                }
                locAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("ITM", "Error getting documents: ", exception)
            }

        // 게시글 올리기 버튼
        // 위치 고르는 액티비티 (MapActivity) 호출
        binding.mapBtn.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}