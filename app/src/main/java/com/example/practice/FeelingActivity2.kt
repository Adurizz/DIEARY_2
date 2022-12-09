package com.example.practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.example.practice.databinding.ActivityFeeling2Binding
import com.example.practice.databinding.ActivityFeelingBinding

class FeelingActivity2 : AppCompatActivity() {

    lateinit var image : String

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

        spinnerFeeling.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0 -> {
                        image = R.drawable.happy.toString()
                    }
                    1 -> {
                        image = R.drawable.angry.toString()
                    }
                    2 -> {
                        image = R.drawable.shy.toString()
                    }
                    3 -> {
                        image = R.drawable.sad.toString()
                    }
                    4 -> {
                        image = R.drawable.shocked.toString()
                    }
                    5 -> {
                        image = R.drawable.sleepy.toString()
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.goBtn2.setOnClickListener {
            val intent = Intent(this, SubActivity2::class.java)
            intent.putExtra("date", date)
            startActivity(intent)
            finish()
        }
    }
}