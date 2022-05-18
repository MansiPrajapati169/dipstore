package com.example.dipstore.common

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.media.RingtoneManager
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BADGE_ICON_SMALL
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat
import com.example.dipstore.R
import com.google.android.material.textfield.TextInputEditText

fun Activity.hideKeyboard() {
    currentFocus?.let {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
    }
}

fun Activity.setIntent(destination: Activity) {
    startActivity(Intent(this, destination::class.java))
    finish()
}

fun Activity.setIntentWithoutFinish(destination: Activity) {
    startActivity(Intent(this, destination::class.java))
}

fun SpannableString.getSpannable(start: Int, end: Int,color: Int, onSpanClick: () -> Unit): Spannable {
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(p0: View) {
            onSpanClick()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
            ds.typeface = Typeface.DEFAULT_BOLD
            ds.color = color
        }
    }
    this.setSpan(clickableSpan,start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun TextInputEditText.emptyValidation() = this.text.toString().isEmpty()//TextUtils.isEmpty(this.text.toString())

const val NOTIFICATION_ID = 100

fun showNotification(context: Context, title: String?, activity: Class<*>, message: String?) {
    val replyLabel = "Enter your reply here"
    val remoteInput = RemoteInput.Builder("key_text_reply")
        .setLabel(replyLabel)
        .build()
    val intent = Intent(context, activity)
    val notification: Notification
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val pi = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_MUTABLE)
        val replyAction = NotificationCompat.Action.Builder (R.drawable.apple, "Reply", pi)
            .addRemoteInput(remoteInput)
            .build()
        val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
        val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.createNotificationChannel(channel)
        notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.apple)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pi)
            .setOnlyAlertOnce(true)
            .setNumber(2)
            .setBadgeIconType(BADGE_ICON_SMALL)
            .setColor(ContextCompat.getColor(context,R.color.button_color))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(replyAction)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentTitle(title).build()
        notificationManager?.notify(NOTIFICATION_ID, notification)
    }
}

fun sleep(durationInSec: Long) {
    try {
        Thread.sleep(1000 * durationInSec, 0)
    } catch (e: InterruptedException) {
        Log.e("exception",e.toString())
    }
}

const val VERBOSE_NOTIFICATION_CHANNEL_NAME =
    "Verbose WorkManager Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
    "Shows notifications whenever work starts"
const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
