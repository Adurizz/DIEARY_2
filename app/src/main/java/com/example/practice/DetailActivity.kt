package com.example.practice

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

import com.example.practice.databinding.ActivityDetailBinding
import com.example.practice.model.Book
import com.example.practice.model.Review

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var db: AppDatabase

    private var model: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = getAppDatabase(this)

        // data 넘겨줌줌
        model = intent.getParcelableExtra("bookModel")

        renderView()

        initSaveButton()
    }

    private fun initSaveButton() {
        binding.saveButton.setOnClickListener {
            Thread {
                db.reviewDao().saveReview(
                    Review(
                        model?.id?.toInt() ?: 0,
                        binding.reviewEditText.text.toString()
                    )
                )
            }.start()
            Toast
                .makeText(this, "Saved !!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun renderView() {

        binding.titleTextView.text = model?.title.orEmpty()

        binding.descriptionTextView.text = model?.description.orEmpty()

        Glide.with(binding.coverImageView.context)
            .load(model?.coverLargeUrl.orEmpty())
            .into(binding.coverImageView)


        // 저장된 리뷰 데이터 가져오기;
        Thread {
            val review = db.reviewDao().getOneReview(model?.id?.toInt() ?: 0)
            review?.let {
                runOnUiThread {
                    binding.reviewEditText.setText(it.review)
                }
            }
        }.start()
    }
}