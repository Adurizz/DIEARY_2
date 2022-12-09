package com.example.practice

import android.app.Activity
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice.databinding.ActivitySub2Binding
import com.example.practice.databinding.ActivityViewBinding

class ViewActivity : AppCompatActivity() {
    val binding by lazy { ActivityViewBinding.inflate(layoutInflater)}
    lateinit var vImage: ImageView
    lateinit var vText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var txtDiaries = mutableListOf<TxtDiaryInfo>()
        txtDiaries.clear()

        val txtAdapter = TxtAdapter(txtDiaries)
        binding.txtDiaryView.adapter = txtAdapter
        binding.txtDiaryView.layoutManager = LinearLayoutManager(this)

        //db에서 데이터 읽어와 리스트에 넣고 visualize
//        db.collection("textDiarys").document(MainActivity.userId).collection("infos")
//            .get()
//            .addOnSuccessListener { documents ->
//                for (document in documents) {
//                    //Log.d("ITM", "${document.id} => ${document.data.get("locName")}")
//                    locations.add(LocInfo(document.data.get("locName").toString(),document.data.get("date").toString(), document.data.get("comments").toString(),document.data.get("lat").toString(), document.data.get("lng").toString()))
//                }
//                locAdapter.notifyDataSetChanged()
//            }
//            .addOnFailureListener { exception ->
//                Log.w("ITM", "Error getting documents: ", exception)
//            }
    }
}