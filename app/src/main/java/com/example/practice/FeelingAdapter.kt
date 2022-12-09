package com.example.practice

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class FeelingAdapter (private val context: Context,
                      private val feelingList: ArrayList<Feeling>): BaseAdapter(){

    override fun getCount(): Int {
        return feelingList.size
    }

    override fun getItem(position: Int): Any {
        return feelingList[position]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {

        val rootView: View = LayoutInflater.from(context)
            .inflate(R.layout.feeling_layout,viewGroup,false)

        //객체생성
        val feelingImage: ImageView = rootView.findViewById(R.id.feeling_image)

        //데이터 담기
        feelingImage.setImageResource(feelingList[position].feeling_image)

        return rootView
    }

}