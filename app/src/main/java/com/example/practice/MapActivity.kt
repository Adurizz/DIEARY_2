package com.example.practice

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.practice.databinding.ActivityLocationBinding
import com.example.practice.databinding.ActivityMapBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

class MapActivity : AppCompatActivity(), MapsFragment.OnLocationPassListener {
    val binding by lazy { ActivityMapBinding.inflate(layoutInflater)}
    lateinit var auth : FirebaseAuth
    lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mapFrangment = MapsFragment()
        db = FirebaseFirestore.getInstance()

        val fManager = supportFragmentManager
        val transaction = fManager.beginTransaction()
        binding.mapDate.text = LocalDate.now().toString()

        transaction.add(binding.mapFrame.id, mapFrangment)
        transaction.commit()

        binding.savBtn.setOnClickListener {
            if (binding.latitudeText.text == "") {
                Toast.makeText(this,"please select location!", Toast.LENGTH_SHORT).show()
            }
            else {
                var addedLoc = LocInfo()
                addedLoc.locName = binding.mapTitle.text.toString()
                addedLoc.date = binding.mapDate.text.toString()
                addedLoc.comments = binding.mapContent.text.toString()
                addedLoc.lat = binding.latitudeText.text.toString()
                addedLoc.lng = binding.longtitudeText.text.toString()
                var postId = binding.mapTitle.text.toString() + binding.mapDate.text.toString()

                db.collection("locations").document(MainActivity.userId).collection("infos").document("$postId")
                    .set(addedLoc)
                    .addOnSuccessListener {
                        Log.d("ITM", "Location info successfully written!")
                        val intent = Intent(this, LocationActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener {
                            e -> Log.w("ITM", "Error writing document", e)
                        val intent = Intent(this, LocationActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

            }
        }
    }

    override fun onLocationPass(lat: String, lng: String) {
        binding.latitudeText.text = lat
        binding.longtitudeText.text = lng
        Log.d("ITM", lat)
        Log.d("ITM", lng)
    }
}