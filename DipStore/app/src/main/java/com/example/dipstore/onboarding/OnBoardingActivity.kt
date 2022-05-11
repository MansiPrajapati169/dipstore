package com.example.dipstore.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.dipstore.R
import com.example.dipstore.databinding.ActivityOnboardingBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
    }

    fun setData() {

        supportActionBar?.hide()
        val onboardingData = OnBoardingData.getOnBoardingData()
        val adapter = OnBoardingAdapter(onboardingData)
        binding.viewPager.adapter = adapter
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout,binding.viewPager){ tab, position -> }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.ivOnboarding.setImageResource(onboardingData[position].image)
                super.onPageSelected(position)
            }
        })
    }
}