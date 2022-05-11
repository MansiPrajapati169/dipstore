package com.example.dipstore.home

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.core.app.ServiceCompat.stopForeground
import com.example.dipstore.R
import com.example.dipstore.common.NOTIFICATION_CHANNEL_ID
import com.example.dipstore.common.NOTIFICATION_ID
import com.example.dipstore.databinding.ActivityDetailBinding
import com.google.android.datatransport.runtime.scheduling.jobscheduling.SchedulerConfig

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
    }

    private fun setData() {
        NotificationManagerCompat.from(this).apply {
          //  stopForeground(true)
            cancelAll()
        }
        val msg = RemoteInput.getResultsFromIntent(intent)?.getCharSequence("key_text_reply")
        binding.tvProductName.text = msg
        val mSpannableString = SpannableString(binding.tvSizeGuide.text)
        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
        binding.apply {
            tvSizeGuide.text = mSpannableString
            ivFirst.setOnClickListener(this@DetailActivity)
            ivSecond.setOnClickListener(this@DetailActivity)
            ivThird.setOnClickListener(this@DetailActivity)
            ivFourth.setOnClickListener(this@DetailActivity)
            ivFifth.setOnClickListener(this@DetailActivity)
            customToolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }
        supportActionBar?.hide()
        binding.btnAddToBag.setOnClickListener {
            val SUMMARY_ID = 0
            val GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL"

            val newMessageNotification1 = NotificationCompat.Builder(this,  NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.apple)
                .setContentTitle("Notification 1")
                .setContentText("You will not believe...")
                .setGroup(GROUP_KEY_WORK_EMAIL)
                .build()

            val newMessageNotification2 = NotificationCompat.Builder(this,  NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.google)
                .setContentTitle("Notification 2")
                .setContentText("Please join us to celebrate the...")
                .setGroup(GROUP_KEY_WORK_EMAIL)
                .build()

            val summaryNotification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.bag)
                .setStyle(NotificationCompat.InboxStyle()
                .setSummaryText("janedoe@example.com"))
                .setGroup(GROUP_KEY_WORK_EMAIL)
                .setGroupSummary(true)
                .build()

            NotificationManagerCompat.from(this).apply {
                notify(101, newMessageNotification1)
                notify(102, newMessageNotification2)
                notify(SUMMARY_ID, summaryNotification)
            }
        }

        binding.btnFollow.setOnClickListener {
            try {
                val intent = Intent(this, NavigationActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

                val pendingIntent: PendingIntent = PendingIntent.getActivity(
                    this, 0, intent, PendingIntent.FLAG_MUTABLE
                )
                val progressMax = 100
                val notification =
                    NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.like)
                        .setContentTitle("Dipstore")
                        .setContentText("Downloading")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setOngoing(true)
                        .setSmallIcon(R.drawable.apple)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                        .setOnlyAlertOnce(true)
                        .setProgress(progressMax, 0, true)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)

                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(1, notification.build())

                Thread(Runnable {
                    SystemClock.sleep(2000)
                    var progress = 0
                    while (progress <= progressMax) {
                        SystemClock.sleep(
                            500
                        )
                        progress += 20
                        notification.setProgress(100, progress, false)
                        notificationManager.notify(1, notification.build())
                    }

                    notification.setContentText("Download complete")
                        .setProgress(0, 0, false)
                        .setOngoing(false)
                    notificationManager.notify(1, notification.build())
                }).start()
            }
            catch (e: Exception) {
                Log.e("error",e.localizedMessage)
                Toast.makeText(this,e.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onClick(p0: View?) {
        p0?.let {
            val image = findViewById<ImageView>(it.id)
            val drawable = image.drawable
            binding.ivDetail.setImageDrawable(drawable)
        }
    }
}