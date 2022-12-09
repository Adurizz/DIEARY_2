package com.example.practice

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice.databinding.ActivitySub2Binding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import java.io.FileInputStream
import java.io.FileOutputStream

class SubActivity2 : AppCompatActivity() {
    val binding by lazy { ActivitySub2Binding.inflate(layoutInflater)}
    lateinit var date : String
    lateinit var saveBtn: Button
    lateinit var diaryTextView: TextView
    lateinit var diaryEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.d("ITM","Sub2 in")

        // UI값 생성
//        diaryTextView=findViewById(R.id.diaryTextView)
        diaryTextView = binding.diaryTextView
        saveBtn=binding.saveBtn
        diaryEditText = binding.diaryEditText

        diaryTextView.visibility = View.VISIBLE // 2022/11/23
        saveBtn.visibility = View.VISIBLE
        diaryEditText.visibility = View.VISIBLE

        if (intent.getStringExtra("date") != null) {
            date = intent.getStringExtra("date")!!
        }
        Log.d("ITM","1")
        Log.d("ITM", "Date is $date")

        val db = Firebase.firestore
        Log.d("ITM","2")
        //리사이클러뷰에 띄울 리스트 생성
        diaryTextView.text = date
        Log.d("ITM","3")
        Log.d("ITM", "Date is $date")


        //db에 일기 넣기
        saveBtn.setOnClickListener {
            var addedDiary = TxtDiaryInfo()
            addedDiary.diaryContents = binding.diaryEditText.text.toString()
            addedDiary.diaryImgSrc = 0
            var diaryId = ""
            for (i in 0..9) {
                val randomChar = ('a'..'z').random()
                diaryId += randomChar
            }
            Log.d("ITM", "This post's id is $diaryId.")

            db.collection("locations").document(MainActivity.userId).collection("infos").document("${diaryId}")
                .set(addedDiary)
                .addOnSuccessListener {
                    Log.d("ITM", "DocumentSnapshot successfully written!")
//                    val intent = Intent(this, LocationActivity::class.java)
//                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                        e -> Log.w("ITM", "Error writing document", e)
                    val intent = Intent(this, LocationActivity::class.java)
                    startActivity(intent)
                    finish()
                }
        }
        Log.d("ITM","4")
    }


}

