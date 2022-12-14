package com.example.practice

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice.databinding.ActivitySub2Binding
import com.example.practice.databinding.ActivityViewBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

// 매일매일의 글 모아서 보는 Activity
class ViewActivity : AppCompatActivity() {
    val binding by lazy { ActivityViewBinding.inflate(layoutInflater)}

    init {
        instance = this
    }
    companion object {
        private var instance:ViewActivity?=null
        fun getInstance():ViewActivity? {
            return instance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = Firebase.firestore
        // 어떤 날짜의 게시글에 접근하고자 하는지 확인 위해 데이터 받아옴
        val date = intent.getStringExtra("date")

        var txtDiaries = mutableListOf<TxtDiaryInfo>()
        txtDiaries.clear()

        val txtAdapter = TxtAdapter(txtDiaries)
        binding.txtDiaryView.adapter = txtAdapter
        binding.txtDiaryView.layoutManager = LinearLayoutManager(this)

        var locations = mutableListOf<LocInfo>()
        locations.clear()

        val locAdapter = LocAdapter(locations)
        binding.locationView.adapter = locAdapter
        binding.locationView.layoutManager =LinearLayoutManager(this)

        var books = mutableListOf<BookInfo>()
        books.clear()

        val bookAdapter = BItemAdapter(books)
        binding.bookView.adapter = bookAdapter
        binding.bookView.layoutManager = LinearLayoutManager(this)

        //db에서 데이터 읽어와 리스트에 넣고 visualize
        db.collection("emojis").document(MainActivity.userId).collection("infos")
            .whereEqualTo("emojiDate", date)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    binding.feelView.setImageResource(document.data.get("emojiSrc").toString().toInt())
                }
            }
            .addOnFailureListener { exception ->
                Log.w("ITM", "Error getting documents: ", exception)
            }
        db.collection("textDiarys").document(MainActivity.userId).collection("infos")
            .whereEqualTo("diaryDate", date)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("ITM", "${document.data.get("diaryContents").toString()}, ${document.data.get("diaryImgSrc").toString()}, ${document.data.get("diaryDate").toString()}")
                    if (document.data.get("diaryImgSrc") == null) {
                        txtDiaries.add(TxtDiaryInfo(document.data.get("diaryContents").toString(), null, document.data.get("diaryDate").toString()))
                    } else {
                        txtDiaries.add(TxtDiaryInfo(document.data.get("diaryContents").toString(), document.data.get("diaryImgSrc").toString(), document.data.get("diaryDate").toString()))
                    }
                }
                txtAdapter.notifyDataSetChanged()
                Log.d("ITM", "data Set")
            }
            .addOnFailureListener { exception ->
                Log.w("ITM", "Error getting documents: ", exception)
            }

        db.collection("books").document(MainActivity.userId).collection("infos")
            .whereEqualTo("userId", MainActivity.userId)
            .whereEqualTo("date", date)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    books.add(BookInfo(document.data.get("bookTitle").toString(),document.data.get("bookImgSrc").toString(), document.data.get("bookComment").toString(),document.data.get("date").toString(), document.data.get("userId").toString()))
                }
                bookAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("ITM", "Error getting documents: ", exception)
            }

        db.collection("locations").document(MainActivity.userId).collection("infos")
            .whereEqualTo("userId", MainActivity.userId)
            .whereEqualTo("date", date)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    locations.add(LocInfo(document.data.get("locName").toString(),document.data.get("date").toString(), document.data.get("comments").toString(),document.data.get("lat").toString(), document.data.get("lng").toString()))
                }
                locAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("ITM", "Error getting documents: ", exception)
            }
    }
}