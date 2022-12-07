package com.example.practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice.databinding.ActivityLocationBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LocationActivity : AppCompatActivity() {
    val binding by lazy { ActivityLocationBinding.inflate(layoutInflater)}
    lateinit var getResult : ActivityResultLauncher<Intent>

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

        val locAdapter = LocAdapter(locations)
        binding.locRecView.adapter = locAdapter
        binding.locRecView.layoutManager =LinearLayoutManager(this)

        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val title = it.data?.getStringExtra("title")
                val date = it.data?.getStringExtra("date")
                val cont = it.data?.getStringExtra("content")
                val lat = it.data?.getStringExtra("latitude")
                val long = it.data?.getStringExtra("longitude")
                if (title != null && date!= null && cont!=null && lat!=null && long!=null) {
                    Log.d("ITM", "$title, $date, $cont, $lat, $long")
                    locations.add(LocInfo(title,date, cont,lat.toString(), long.toString()))
                    locAdapter.notifyDataSetChanged()
                }
            }
        }

        binding.mapBtn.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            getResult.launch(intent)
        }
    }
}