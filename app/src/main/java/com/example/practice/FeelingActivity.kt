package com.example.practice

import android.R
import android.app.Activity
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.example.practice.databinding.ActivityFeelingBinding
import com.example.practice.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class FeelingActivity : AppCompatActivity() {
    lateinit var image : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFeelingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myList =listOf("feeling1", "feeling2", "feeling3", "feeling4", "feeling5")

        val date = intent.getStringExtra("date")


        val myAdapter = ArrayAdapter<String>(this, R.layout.simple_list_item_1, myList)
        binding.spinner.adapter = myAdapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0 -> {
                        binding.imageView.setImageResource(R.drawable.btn_minus)
                        image = R.drawable.btn_minus.toString()
                    }
                    1 -> {
                        binding.imageView.setImageResource(R.drawable.btn_plus)
                        image = R.drawable.btn_plus.toString()
                    }
                    2 -> {
                        binding.imageView.setImageResource(R.drawable.btn_star)
                        image = R.drawable.btn_star.toString()
                    }
                    3 -> {
                        binding.imageView.setImageResource(R.drawable.btn_dialog)
                        image = R.drawable.btn_dialog.toString()
                    }
                    4 -> {
                        binding.imageView.setImageResource(R.drawable.btn_dropdown)
                        image = R.drawable.btn_dropdown.toString()
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.goBtn2.setOnClickListener {
            val intent = Intent(this, SubActivity2::class.java)
            intent.putExtra("date", date)
            Log.d("ITM", "$image is passed")
            //image source pass
            intent.putExtra("image", image)
            startActivity(intent)
            finish()
        }
    }
}