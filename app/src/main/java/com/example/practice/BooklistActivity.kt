package com.example.practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice.databinding.ActivityBooklistBinding
import com.example.practice.databinding.ActivityLocationBinding
import com.example.practice.model.Book
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

class BooklistActivity : AppCompatActivity() {
    val binding by lazy { ActivityBooklistBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = Firebase.firestore

        var books = mutableListOf<BookInfo>()
        books.clear()

        val bookAdapter = BItemAdapter(books)
        binding.bookRecView.adapter = bookAdapter
        binding.bookRecView.layoutManager = LinearLayoutManager(this)

        db.collection("books").document(MainActivity.userId).collection("infos")
            .whereEqualTo("userId", MainActivity.userId)
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

        binding.addBtn.setOnClickListener {
            val intent = Intent(this, BookActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}