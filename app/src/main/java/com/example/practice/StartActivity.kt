package com.example.practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.practice.databinding.ActivityStartBinding

// 시작 화면
class StartActivity : AppCompatActivity() {
    val binding by lazy { ActivityStartBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 일정 시간 지연 이후 실행하기 위한 코드
        Handler(Looper.getMainLooper()).postDelayed({

            // 일정 시간이 지나면 LoginActivity 로 이동
            val intent= Intent( this,LoginActivity::class.java)
            startActivity(intent)

            // 이전 키를 눌렀을 때 스플래스 화면으로 이동을 방지하기 위해 이동한 다음 사용안함으로 finish 처리
            finish()
        }, 1500) // 시간 2초 이후 실행
    }
}