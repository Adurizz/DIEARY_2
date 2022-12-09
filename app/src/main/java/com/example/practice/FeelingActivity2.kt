package com.example.practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.example.practice.databinding.ActivityFeeling2Binding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

class FeelingActivity2 : AppCompatActivity() {

    lateinit var image : String

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityFeeling2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = Firebase.firestore

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
                        Log.d("ITM", image)
                    }
                    1 -> {
                        image = R.drawable.angry.toString()
                        Log.d("ITM", image)
                    }
                    2 -> {
                        image = R.drawable.shy.toString()
                        Log.d("ITM", image)
                    }
                    3 -> {
                        image = R.drawable.sad.toString()
                        Log.d("ITM", image)
                    }
                    4 -> {
                        image = R.drawable.shocked.toString()
                        Log.d("ITM", image)
                    }
                    5 -> {
                        image = R.drawable.sleepy.toString()
                        Log.d("ITM", image)
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.goBtn2.setOnClickListener {
            val intent = Intent(this, SubActivity2::class.java)
            intent.putExtra("date", date)
            var imageSrc = emojiInfo()
            imageSrc.emojiSrc = image.toInt()
            Log.d("ITM", "${imageSrc.emojiSrc}")
//            imageSrc.emojiDate = LocalDate.now().toString() : 오늘만 글쓰게 할 경우
            imageSrc.emojiDate = date
            db.collection("emojis").document(MainActivity.userId).collection("infos").document("${imageSrc.emojiDate}")
                .set(imageSrc)
                .addOnSuccessListener {
                    Log.d("ITM", "Emoji successfully written!")
                    finish()
                }
                .addOnFailureListener {
                        e -> Log.w("ITM", "Error writing document", e)
                    finish()
                }
            startActivity(intent)
            finish()
        }
    }
}