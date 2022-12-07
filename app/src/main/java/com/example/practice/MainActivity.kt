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
import com.example.practice.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    private var auth : FirebaseAuth? = null

    var now = LocalDate.now().toString()
    var arr = now.split("-")
    var date = String.format("%d / %d / %d", arr[0], arr[1], arr[2])
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

    lateinit var viewBtn: Button
    lateinit var locBtn: Button
    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    val SUBACTIVITY_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("ITM",date)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth

//        Log.d("ITM", "${auth?.currentUser?.uid}")

        drawerLayout = binding.drawerLayout
        navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)

        binding.title.text = "If I Dieary"

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Log.d("ITM","calendar in: $view, $year, $month, $dayOfMonth")
            date = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
            Log.d("ITM","calendar in: date= $date")

        }

        binding.goBtn.setOnClickListener {
            try{
                if(date==null)
                    date = LocalDate.now().toString()
                Log.d("ITM","gobtn in")
                val intent = Intent(this, FeelingActivity::class.java)
                intent.putExtra("date", date)
                startActivity(intent)

            }
            catch (e : Exception){
                Log.d("ITM",date)
            }

        }

        binding.viewBtn.setOnClickListener {
            Log.d("ITM", "viewBtn in")
            val intent = Intent(this, ViewActivity::class.java)
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
            R.id.menu_search-> {
                //search activity 여기에 연결
                Log.d("ITM", "search")
            }
            R.id.menu_contents-> {
                //content activity 여기에 연결
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
}