package com.example.practice

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practice.databinding.BookViewBinding
import java.util.*

class BItemAdapter(val bookItems : MutableList<BookInfo>): RecyclerView.Adapter<BItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BookViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val books = bookItems.get(position)
        holder.bind(books)
    }

    override fun getItemCount(): Int {
        return bookItems.size
    }

    class ViewHolder(val binding: BookViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(bookInfo: BookInfo) {
            binding.bookTitle.text = bookInfo.bookTitle
            if (bookInfo.bookImgSrc != null) {
                Glide.with(binding.bookImg.context)
                    .load(bookInfo.bookImgSrc)
                    .into(binding.bookImg)
            }
            binding.bookReview.text = bookInfo.bookComment
        }
    }
}