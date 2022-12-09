package com.example.practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Spinner
import com.example.practice.databinding.ActivityFeeling2Binding
import com.example.practice.databinding.ActivityFeelingBinding

class FeelingActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityFeeling2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val date = intent.getStringExtra("date")

        //스피너 객체 생성
        val spinnerFeeling: Spinner = findViewById(R.id.feeling_spinner)

        //리스트 생성
        val feelingList: ArrayList<Feeling> = ArrayList<Feeling>()

        //데이터 생성
        val happy: Feeling = Feeling(R.drawable.happy)
        val angry: Feeling = Feeling(R.drawable.angry)
        val shy: Feeling = Feeling(R.drawable.shy)
        val sad: Feeling = Feeling(R.drawable.sad)
        val shocked: Feeling = Feeling(R.drawable.shocked)
        val sleepy: Feeling = Feeling(R.drawable.sleepy)

        //데이터 리스트에 담기
        feelingList.add(happy)
        feelingList.add(angry)
        feelingList.add(shy)
        feelingList.add(sad)
        feelingList.add(shocked)
        feelingList.add(sleepy)

        //어댑터 생성
        val adapter: FeelingAdapter = FeelingAdapter(this, feelingList)

        //어댑터 적용
        spinnerFeeling.adapter = adapter

        binding.goBtn2.setOnClickListener {
            val intent = Intent(this, SubActivity2::class.java)
            intent.putExtra("date", date)
            //Log.d("ITM", "$image is passed")
            //image source pass
            //intent.putExtra("image", image)
            startActivity(intent)
            finish()
        }
    }
}