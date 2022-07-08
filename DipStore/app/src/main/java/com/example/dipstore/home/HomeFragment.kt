package com.example.dipstore.home

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.example.dipstore.navigationView.DrawerLayoutActivity
import com.example.dipstore.authentication.SignInActivity
import com.example.dipstore.common.setIntent
import com.example.dipstore.common.setIntentWithoutFinish
import com.example.dipstore.common.showNotification
import com.example.dipstore.database.DatabaseActivity
import com.example.dipstore.databinding.FragmentHomeBinding
import com.example.dipstore.sharedPreferences.SharedPreferenceHelper

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPreference: SharedPreferenceHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setData()
        sharedPreference = SharedPreferenceHelper(requireActivity())
        return binding.root
    }

    private fun setData() {
     //   activity?.actionBar?.show()
        val sellData = SellData.getSellData()
        val popularData = SellData.getPopularData()
        val sellAdapter = SellAdapter(sellData)
        val popularAdapter = SellAdapter(popularData)
        binding.apply {
            rvSell.adapter = sellAdapter
            rvPopular.adapter = popularAdapter
        }

        binding.ivTime.setOnClickListener {
            activity?.setIntentWithoutFinish(DatabaseActivity())
        }

        binding.ivCart.setOnClickListener {
            val isLoggedIn = sharedPreference.getPrefs<Boolean>("IsLoggedIn")
            if (isLoggedIn == true) {
                sharedPreference.putPrefs(false,"IsLoggedIn")
                requireActivity().setIntent(SignInActivity())
            }
        }

        binding.ivEmail.setOnClickListener {
            requireActivity().setIntent(DrawerLayoutActivity())
        }

        binding.btnShopNow.setOnClickListener {
            val notification = NotificationManagerCompat.from(requireContext())
            val isEnabled = notification.areNotificationsEnabled()

            if (!isEnabled) {
                val alertDialog: AlertDialog = AlertDialog.Builder(requireContext())
                    .setTitle("Enable Notifications?")
                    .setMessage("Please open notification permission in Notification")
                    .setNegativeButton("cancel") { dialog, _ -> dialog.cancel() }
                    .setPositiveButton("Setting") { dialog, _ ->
                        dialog.cancel()
                        val intent = Intent()
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                            intent.putExtra("android.provider.extra.APP_PACKAGE", requireContext().packageName)
                        }
                        startActivity(intent)
                    }.create()
                alertDialog.show()
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
            }
            else {
                showNotification(requireContext(),"hello",DetailActivity::class.java,"First Notification")
            }
        }
    }
}