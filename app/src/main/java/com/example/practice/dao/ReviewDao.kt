package com.example.practice.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.practice.model.Review

@Dao
interface ReviewDao {

    @Query("SELECT * FROM review WHERE id = :id")
    fun getOneReview(id: Int): Review

    // 같은 값이오면 새로운 거로 대체.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveReview(review: Review)
}