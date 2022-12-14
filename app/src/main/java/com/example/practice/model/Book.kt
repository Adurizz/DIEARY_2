package com.example.practice.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


// SerializedName 서버에서 받아오는 명칭을 적용해줄 수 있음
// DEA DTO
@Parcelize // 직렬화 가능하도록 수정 (인텐트로 넘겨주기 위해서)
data class Book(
    @SerializedName("itemId") val id: Long = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("coverSmallUrl") val coverSmallUrl: String = "",
    @SerializedName("coverLargeUrl") val coverLargeUrl: String = ""
): Parcelable