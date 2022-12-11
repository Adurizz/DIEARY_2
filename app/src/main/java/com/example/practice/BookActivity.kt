package com.example.practice

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.example.practice.adapter.BookAdapter
import com.example.practice.adapter.HistoryAdapter
import com.example.practice.api.BookService
import com.example.practice.databinding.ActivityBookBinding
import com.example.practice.model.BestSellerDto
import com.example.practice.model.History
import com.example.practice.model.SearchBookDto

//외부(인터파크) 도서검색 API 액티비티
class BookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookBinding
    private lateinit var bookRecyclerViewAdapter: BookAdapter
    private lateinit var bookService: BookService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBookRecyclerView()
        initSearchEditText()

        initBookService()
        bookServiceLoadBestSellers()
    }

    private fun bookServiceLoadBestSellers() {
        // 베스트 셀러 리스트 가져오기
        // 처음에 보여지는 리스트는 베스트 셀러 리스트.. 검색 하면 검색결과에 해당하는 리스트 리턴
        // 검색 결과는 베스트 셀러에서만 검색하는게 아니라 전체 도서 리스트에서 검색해서 결과 반환
        bookService.getBestSellerBooks("0E612B1807DB01FD0959C8FF5E9AEA75D6C7B4BD394F2B8FD45D9EB9813D3A38")
            .enqueue(object : Callback<BestSellerDto> {
                // 응답이 온 경우
                override fun onResponse(
                    call: Call<BestSellerDto>,
                    response: Response<BestSellerDto>
                ) {
                    // 받은 응답이 성공한 응답일 때
                    if (response.isSuccessful.not()) {
                        Log.e(M_TAG, "NOT!! SUCCESS")
                        return
                    }

                    // 받은 응답의 바디가 채워져 있는 경우만 진행
                    response.body()?.let {
                        Log.d(M_TAG, it.toString())

                        it.books.forEach { book ->
                            Log.d(M_TAG, book.toString())
                        }

                        // 새 리스트로 갱신
                        bookRecyclerViewAdapter.submitList(it.books)
                    }
                }

                // 응답에 실패한 경우
                override fun onFailure(call: Call<BestSellerDto>, t: Throwable) {
                    Log.e(M_TAG, t.toString())
                }
            })
    }

    private fun initBookService() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://book.interpark.com/") // 인터파크 베이스 주소;
            .addConverterFactory(GsonConverterFactory.create()) // Gson 변환기 사용;
            .build()

        bookService = retrofit.create(BookService::class.java)
    }

    private fun initBookRecyclerView() {
        //검색 결과로 나온 도서 리스트에서 한 도서를 선택(클릭) 했을 경우 호출
        //DetailActivity로 연결
        bookRecyclerViewAdapter = BookAdapter(itemClickedListener = {
            //Detail Activity = 해당 도서에 한줄평 첨가해 db에 추가하는 activity
            var intent = Intent(this, DetailActivity::class.java)

            // 직렬화 해서 넘길 것.
            intent.putExtra("bookModel", it)
            startActivity(intent)
            finish()
        })

        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookRecyclerView.adapter = bookRecyclerViewAdapter
    }


    fun bookServiceSearchBook(keyword: String) {
        //책 검색 관련. 검색 진행 범위는 베스트셀러 내에서가 아닌 전체 도서
        bookService.getBooksByName("0E612B1807DB01FD0959C8FF5E9AEA75D6C7B4BD394F2B8FD45D9EB9813D3A38", keyword)
            .enqueue(object : Callback<SearchBookDto> {
                // 성공.

                override fun onResponse(
                    call: Call<SearchBookDto>,
                    response: Response<SearchBookDto>
                ) {

                    if (response.isSuccessful.not()) {
                        return
                    }

                    bookRecyclerViewAdapter.submitList(response.body()?.books.orEmpty()) // 새 리스트로 갱신
                }

                // 실패.
                override fun onFailure(call: Call<SearchBookDto>, t: Throwable) {
                    Log.e(M_TAG, t.toString())
                }
            })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initSearchEditText() {
        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            // 키보드 입력시 발생

            // 엔터 눌렀을 경우 (눌렀거나, 뗏을 때 -> 눌렀을 때 발생하도록.)
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN) {
                bookServiceSearchBook(binding.searchEditText.text.toString())
                return@setOnKeyListener true// 처리 되었음.
            }
            return@setOnKeyListener false // 처리 안됬음 을 나타냄.
        }

        binding.searchEditText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
            }
            return@setOnTouchListener false
        }
    }

    companion object {
        private const val M_TAG = "BookActivity"
    }
}