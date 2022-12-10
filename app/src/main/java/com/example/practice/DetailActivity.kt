package com.example.practice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

import com.example.practice.databinding.ActivityDetailBinding
import com.example.practice.model.Book
import com.example.practice.model.Review
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

class DetailActivity : AppCompatActivity() {
    val db = Firebase.firestore

    private lateinit var binding: ActivityDetailBinding

//    private lateinit var db: AppDatabase

    private var model: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // data 넘겨줌줌
        model = intent.getParcelableExtra("bookModel")

        renderView()

        initSaveButton()
    }

    private fun initSaveButton() {
        binding.saveButton.setOnClickListener {
            var addedBook = BookInfo()
            addedBook.bookTitle = binding.titleTextView.text.toString()
            addedBook.date = LocalDate.now().toString()
            addedBook.bookImgSrc = model?.coverLargeUrl
            addedBook.bookComment = binding.reviewEditText.text.toString()
            addedBook.userId = MainActivity.userId
            var bookId = binding.titleTextView.text.toString() + addedBook.date

            db.collection("books").document(MainActivity.userId).collection("infos").document("$bookId")
                .set(addedBook)
                .addOnSuccessListener {
                    Log.d("ITM", "Book info successfully written!")
                    val intent = Intent(this, BooklistActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                        e -> Log.w("ITM", "Error writing document", e)
                    finish()
                }
        }
    }

    private fun renderView() {

        binding.titleTextView.text = model?.title.orEmpty()

        binding.descriptionTextView.text = model?.description.orEmpty()

        Glide.with(binding.coverImageView.context)
            .load(model?.coverLargeUrl.orEmpty())
            .into(binding.coverImageView)
    }
}