package com.example.dipstore

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.dipstore.authentication.SignInActivity
import com.example.dipstore.authentication.SignUpActivity
import com.example.dipstore.common.setIntent
import com.example.dipstore.home.NavigationActivity
import com.example.dipstore.onboarding.OnBoardingActivity
import com.example.dipstore.sharedPreferences.SharedPreferenceHelper
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes


class MainActivity : AppCompatActivity() {
   private var sharedPreferences = SharedPreferenceHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCenter.start(
            application,
            "036bc1ab-6ef7-406a-b782-63ff04a458b2",
            Analytics::class.java,
            Crashes::class.java
        )
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val prevStarted = sharedPreferences.getPrefs<Boolean>("PrevStarted")
        when {
            prevStarted == true && sharedPreferences.getPrefs<Boolean>("IsLoggedIn") == true -> this.setIntent(NavigationActivity())
            sharedPreferences.getPrefs<Boolean>("IsLoggedIn") == false -> this.setIntent(SignUpActivity())
            prevStarted == true && sharedPreferences.getPrefs<Boolean>("Registered") == false -> this.setIntent(SignInActivity())
            else -> this.setIntent(OnBoardingActivity())
        }
        finish()
    }
}