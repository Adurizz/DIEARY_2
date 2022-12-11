package com.example.practice

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice.databinding.ActivitySub2Binding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

class SubActivity2 : AppCompatActivity() {
    val binding by lazy { ActivitySub2Binding.inflate(layoutInflater)}
    lateinit var saveBtn: Button
    lateinit var diaryTextView: TextView
    lateinit var diaryEditText: EditText
    var uri : Uri? = null

    //upload photo
    private var activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode== RESULT_OK&&it.data != null) {
            uri = it.data!!.data!!

            Glide.with(this)
                .load(uri)
                .into(binding.photoImage)
        }
    }//upload photo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.d("ITM","Sub2 in")

        // UI값 생성
//        diaryTextView=findViewById(R.id.diaryTextView)
        diaryTextView = binding.diaryTextView
        saveBtn=binding.saveBtn
        diaryEditText = binding.diaryEditText

        diaryTextView.visibility = View.VISIBLE // 2022/11/23
        saveBtn.visibility = View.VISIBLE
        diaryEditText.visibility = View.VISIBLE

        val db = Firebase.firestore

        //리사이클러뷰에 띄울 리스트 생성
        diaryTextView.text = LocalDate.now().toString()

        binding.galleryBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            activityResult.launch(intent)
        }



        //db에 일기 넣기
        saveBtn.setOnClickListener {
            if (binding.diaryEditText.text.toString() == "") {
                finish()
            }else {
                var addedDiary = TxtDiaryInfo()
                addedDiary.diaryContents = binding.diaryEditText.text.toString()
                if (uri != null) {
                    // uri를 bitmap으로 변환후 string으로 encoding (db에 String 형식으로 넣기 위함)
                    // size exception 피하기 위해 resize 진행
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    val resWidth = sqrt((10.0).pow(5) * bitmap.width/bitmap.height)
                    val widVersusHeight : Float =  bitmap.height.toFloat()/bitmap.width.toFloat()
                    val resHeight = widVersusHeight * resWidth
                    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, resWidth.toInt(), resHeight.toInt(), true)
                    val stream= ByteArrayOutputStream()
                    resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    val bytes = stream.toByteArray()
                    val bitString = Base64.getEncoder().encodeToString(bytes)
                    if (bitString != null) {
                        addedDiary.diaryImgSrc = bitString
                        Log.d("ITM", "source code is $bitString")
                    } else {
                        addedDiary.diaryImgSrc = null
                    }
                }
                addedDiary.diaryDate = LocalDate.now().toString()
                //각 diary 게시글 id 부여(랜덤 문자열)
                var diaryId = ""
                for (i in 0..9) {
                    val randomChar = ('a'..'z').random()
                    diaryId += randomChar
                }
                Log.d("ITM", "This post's id is $diaryId.")

                db.collection("textDiarys").document(MainActivity.userId).collection("infos").document("${diaryId}")
                    .set(addedDiary)
                    .addOnSuccessListener {
                        Log.d("ITM", "Text diary successfully written!")
                        finish()
                    }
                    .addOnFailureListener {
                            e -> Log.w("ITM", "Error in writing Text diary", e)
                        Toast.makeText(this,"Save failed : The picture file's size is too big!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
            }
        }
        Log.d("ITM","4")
    }


}

