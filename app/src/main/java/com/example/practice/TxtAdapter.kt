package com.example.practice

import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practice.databinding.LocViewBinding
import com.example.practice.databinding.TxtdiaryViewBinding
import java.util.*

class TxtAdapter(val txtDiary : MutableList<TxtDiaryInfo>): RecyclerView.Adapter<TxtAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TxtdiaryViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val txts = txtDiary.get(position)
        holder.bind(txts)
    }

    override fun getItemCount(): Int {
        return txtDiary.size
    }

    class ViewHolder(val binding: TxtdiaryViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(txtDiaryInfo: TxtDiaryInfo) {
            binding.diaryTxt.text = txtDiaryInfo.diaryContents
            if (txtDiaryInfo.diaryImgSrc != null) {
                val encodeByte: ByteArray = Base64.getDecoder().decode(txtDiaryInfo.diaryImgSrc)
                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                binding.diaryImg.setImageBitmap(bitmap)
            }
        }
    }
}