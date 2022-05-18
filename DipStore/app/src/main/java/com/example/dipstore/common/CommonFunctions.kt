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
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BADGE_ICON_SMALL
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

var NOTIFICATION_CHANNEL_ID = "net.larntech.notification"
val NOTIFICATION_ID = 100
@RequiresApi(Build.VERSION_CODES.S)
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
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, title, NotificationManager.IMPORTANCE_DEFAULT)
        notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
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
        notificationManager.createNotificationChannel(notificationChannel)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}