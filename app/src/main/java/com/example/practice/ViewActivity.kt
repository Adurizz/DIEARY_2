package com.example.practice

import android.app.Activity
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView

class ViewActivity : AppCompatActivity() {
    lateinit var vImage: ImageView
    lateinit var vText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        vImage=findViewById(R.id.vImage)
        vText=findViewById(R.id.vText)

        if (intent != null) {
            var imgSource = intent.getStringExtra("feelingImgSrc")?.toInt()
            Log.d("ITM","$imgSource")
            if (imgSource != null) {
                vImage.setImageResource(imgSource)
            }
            vText.text = intent.getStringExtra("textDiary")
        }
    }
}