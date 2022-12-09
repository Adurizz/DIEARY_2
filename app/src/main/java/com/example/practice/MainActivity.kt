package com.example.practice

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import java.io.FileInputStream
import java.io.FileOutputStream

import android.view.View
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice.api.BookService
import com.example.practice.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    private var auth : FirebaseAuth? = null
    private lateinit var bookService: BookService
//    var now = LocalDate.now().toString()
//    var arr = now.split("-")
//    var date = String.format("%s/%s/%s", arr[0], arr[1], arr[2])


    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    init {
        instance = this
    }
    companion object {
        lateinit var userId : String
        private var instance:MainActivity?=null
        fun getInstance():MainActivity? {
            return instance
        }
    }



    val SUBACTIVITY_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        var date = LocalDate.now().toString()
        Log.d("ITM",date)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth

        userId = auth?.currentUser?.uid.toString()

        Log.d("ITM", "UID : ${userId} logged in.")
        Log.d("ITM", "Date is : $date")
        //드로어
        drawerLayout = binding.drawerLayout
        navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)


        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var day = ""
            if (dayOfMonth.toString().length == 1) {
                day = "0" + dayOfMonth.toString()
                Log.d("ITM", day)
            } else {
                day = dayOfMonth.toString()
            }
            date = String.format("%d-%d-%s", year, month + 1, day)
            Log.d("ITM", "Selected date is : $date")
        }

        binding.goBtn.setOnClickListener {
            Log.d("ITM","gobtn in")
            val intent = Intent(this, FeelingActivity2::class.java)
            intent.putExtra("date", date)
            Log.d("ITM", "Passed date is : $date")
            startActivity(intent)
        }

        binding.viewBtn.setOnClickListener {
            Log.d("ITM", "viewBtn in")
            val intent = Intent(this, ViewActivity::class.java)
            intent.putExtra("date", date)
            startActivity(intent)
        }

        binding.menuBtn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item!!.itemId){
            android.R.id.home->{
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_home-> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            R.id.menu_contents-> {
                //content activity 여기에 연결
                val intent = Intent(this, BookActivity::class.java)
                startActivity(intent)
                Log.d("ITM", "content")
            }
            R.id.menu_locations-> {
                val intent = Intent(this, LocationActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_logout-> {
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                auth?.signOut()
            }
        }
        return false
    }



    private fun initBookService() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://book.interpark.com/") // 인터파크 베이스 주소;
            .addConverterFactory(GsonConverterFactory.create()) // Gson 변환기 사용;
            .build()

        bookService = retrofit.create(BookService::class.java)
    }


}