package com.example.dipstore.forgotpassword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.dipstore.R
import com.example.dipstore.authentication.SignInActivity
import com.example.dipstore.authentication.ViewPagerAdapter
import com.example.dipstore.common.hideKeyboard
import com.example.dipstore.common.setIntent
import com.example.dipstore.databinding.ActivityForgotPasswordBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setData()
        setContentView(binding.root)
        viewPagerTask(binding.viewPager)
    }

    private fun setData() {
        binding.clForgotPassword.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun viewPagerTask(viewPager: ViewPager2) {
        val titles = resources.getStringArray(R.array.page_names)
        viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(
            binding.tlPasswordRecovery,
            viewPager
        ) { myTabLayout: TabLayout.Tab, position: Int ->
            myTabLayout.text = titles[position]
        }.attach()
        supportActionBar?.hide()
        binding.customToolbar.setNavigationOnClickListener {
            setIntent(SignInActivity())
        }
    }
}