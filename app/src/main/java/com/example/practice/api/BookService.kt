package com.example.practice.api

import com.example.practice.model.BestSellerDto
import com.example.practice.model.SearchBookDto

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    // get : 데이터 요청 시 반환 http
    // post : http body에 넣어 전달

    // 책 검색.
    @GET("/api/search.api?output=json")
    fun getBooksByName(
        @Query("key") apiKey: String,
        @Query("query") kweyWord: String
    ): Call<SearchBookDto>

    // best seller 받아오기
    @GET("/api/bestSeller.api?output=json&categoryId=100")
    fun getBestSellerBooks(
        @Query("key") apiKey: String,
    ): Call<BestSellerDto>

}