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
import com.example.dipstore.common.showNotification
import com.example.dipstore.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setData()
        return binding.root
    }

    private fun setData() {
        val sellData = SellData.getSellData()
        val popularData = SellData.getPopularData()
        val sellAdapter = SellAdapter(sellData)
        val popularAdapter = SellAdapter(popularData)
        binding.apply {
            rvSell.adapter = sellAdapter
            rvPopular.adapter = popularAdapter
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