package com.example.dipstore.authentication

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.dipstore.R

class CustomSpinnerAdapter(private val context: Context, private val images: List<Int>) : BaseAdapter() {
    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(p0: Int): Any {
        return images[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_spinner, p2,false)
        val icon = view.findViewById<ImageView>(R.id.ivFlags)
        icon.setImageResource(images[p0])
        return view
    }
}