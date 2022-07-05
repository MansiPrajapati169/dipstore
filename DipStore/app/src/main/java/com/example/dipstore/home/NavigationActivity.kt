package com.example.dipstore.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dipstore.R
import com.example.dipstore.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationBind()
    }

    private fun bottomNavigationBind() {
        val navController= findNavController(R.id.navFragment)
        binding.bottomJetpackNavigation.setupWithNavController(navController)
        supportActionBar?.hide()
    }
}