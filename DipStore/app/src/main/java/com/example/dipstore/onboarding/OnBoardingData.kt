package com.example.dipstore.onboarding

import com.example.dipstore.R

data class OnBoardingData(
    val image: Int,
    val title: String,
    val description: String
) {
    companion object {
        fun getOnBoardingData() : ArrayList<OnBoardingData> {
            val onBoardingData: ArrayList<OnBoardingData> = arrayListOf()
            onBoardingData.add(OnBoardingData(R.drawable.onboarding,"Welcome to Dipstore","Get exclusive limited apparel that only you have! Made by famous brands in the world"))
            onBoardingData.add(OnBoardingData(R.drawable.onboarding_second,"Everything You Love","Get exclusive limited apparel that only you have! Made by famous brands in the world"))
            return onBoardingData
        }
    }
}
