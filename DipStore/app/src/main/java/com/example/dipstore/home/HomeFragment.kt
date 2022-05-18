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
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.dipstore.common.setIntent
import com.example.dipstore.common.setIntentWithoutFinish
import com.example.dipstore.common.showNotification
import com.example.dipstore.database.DatabaseActivity
import com.example.dipstore.databinding.FragmentHomeBinding
import com.example.dipstore.notifications.NotificationWorker
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setData()
        return binding.root
    }
    fun createConstraints() = Constraints.Builder()
        .setRequiresBatteryNotLow(true)
        .build()

    fun createWorkRequest(data: Data) = PeriodicWorkRequest.Builder(NotificationWorker::class.java, 15, TimeUnit.SECONDS)
        .setInputData(data)
        .setConstraints(createConstraints())
        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
        .build()

    fun startWork() {
        val work = createWorkRequest(Data.EMPTY)
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork("Sleep work", ExistingPeriodicWorkPolicy.REPLACE, work)
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

        binding.ivCart.setOnClickListener {
            startWork()
        }

        binding.ivTime.setOnClickListener {
            activity?.setIntentWithoutFinish(DatabaseActivity())
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