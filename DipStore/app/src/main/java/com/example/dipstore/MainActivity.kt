package com.example.dipstore

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.dipstore.authentication.SignInActivity
import com.example.dipstore.onboarding.OnBoardingActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val prevStarted = "yes"
        val sharedPreferences = getSharedPreferences(getString(R.string.app_name), 0)
        if (sharedPreferences.getBoolean(prevStarted, false)) {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        else {
            startActivity(Intent(this, OnBoardingActivity::class.java))
        }
        finish()
    }
}