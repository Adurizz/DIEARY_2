package com.example.practice

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import java.io.FileInputStream
import java.io.FileOutputStream

import android.view.View
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.practice.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    private var auth : FirebaseAuth? = null

    var userID: String = "userID"
    lateinit var fname: String
    lateinit var str: String
    lateinit var calendarView: CalendarView
    lateinit var updateBtn: Button
    lateinit var deleteBtn:Button
    lateinit var saveBtn:Button
    lateinit var diaryTextView: TextView
    lateinit var diaryContent:TextView
    lateinit var title:TextView
    lateinit var contextEditText: EditText
    lateinit var goBtn: Button  // add
    lateinit var date: String   // add
    lateinit var viewBtn: Button
    lateinit var locBtn: Button

    val SUBACTIVITY_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.title.text = "If I Dieary"
        // 로그아웃
        binding.logoutbutton.setOnClickListener {
            // 로그인 화면으로
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            auth?.signOut()
        }

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Log.d("ITM","calendar in: $view, $year, $month, $dayOfMonth")
            date = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
            Log.d("ITM","calendar in: date= $date")
        }

        binding.goBtn.setOnClickListener {
            Log.d("ITM","gobtn in")
            val intent = Intent(this, FeelingActivity::class.java)
            intent.putExtra("date", date)
            startActivity(intent)
        }

        binding.viewBtn.setOnClickListener {
            Log.d("ITM", "viewBtn in")
            val intent = Intent(this, ViewActivity::class.java)
            startActivity(intent)
        }

        binding.locBtn.setOnClickListener {
            Log.d("ITM", "move to location activity")
            val intent = Intent(this, LocationActivity::class.java)
            startActivity(intent)
        }
    }
}