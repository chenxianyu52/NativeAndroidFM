package com.xianyu.androidlibrary.view.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xianyu.androidlibrary.R

class BaseViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView){
    val name: TextView = itemView.findViewById<TextView>(R.id.name)

}