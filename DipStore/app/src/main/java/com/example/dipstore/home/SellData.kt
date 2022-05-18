package com.example.dipstore.home

import android.content.Context
import com.example.dipstore.R

data class SellData(
    val image: Int,
    val title: String,
    val basePrice: String? = null,
    val finalPrice: String,
    var likeStatus: Boolean
) {
    companion object {
        fun getSellData(): ArrayList<SellData> {
            val sellList: ArrayList<SellData> = ArrayList()
            sellList.add(SellData(R.drawable.sell_first,"Basic High Dpstr", "$45.90","$25.90",false))
            sellList.add(SellData(R.drawable.sell_first,"Basic High Dpstr","$45.90","$25.90",false))
            sellList.add(SellData(R.drawable.popular_first,"Basic High Dpstr","$45.90","$25.90",false))
            return sellList
        }

        fun getPopularData(): ArrayList<SellData> {
            val sellList: ArrayList<SellData> = ArrayList()
            sellList.add(SellData(R.drawable.sell_first,"Basic High Dpstr",null,"$25.90",false))
            sellList.add(SellData(R.drawable.sell_first,"Basic High Dpstr",null,"$25.90",false))
            sellList.add(SellData(R.drawable.popular_first,"Basic High Dpstr",null,"$25.90",false))
            return sellList
        }
    }
}
