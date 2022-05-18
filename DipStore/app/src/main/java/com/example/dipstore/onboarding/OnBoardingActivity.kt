package com.example.dipstore.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.dipstore.R
import com.example.dipstore.authentication.SignInActivity
import com.example.dipstore.authentication.SignUpActivity
import com.example.dipstore.databinding.ActivityOnboardingBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setData()
    }

    private fun setData() {
        supportActionBar?.hide()
        val onboardData = OnBoardingData.getOnBoardingData()
        val adapter = OnBoardingAdapter(onboardData)
        binding.viewPager.adapter = adapter
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout,binding.viewPager){ _, _ -> }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.ivOnboarding.setImageResource(onboardData[position].image)
                super.onPageSelected(position)
            }
        })

        binding.btnSignIn.setOnClickListener {
            val prevStarted = "yes"
            val sharedPreferences = getSharedPreferences("DipStore",0)
            if (!sharedPreferences.getBoolean(prevStarted, false)) {
                val editor = sharedPreferences.edit()
                editor.putBoolean(prevStarted, true)
                editor.apply()
            }
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnGetStarted.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}