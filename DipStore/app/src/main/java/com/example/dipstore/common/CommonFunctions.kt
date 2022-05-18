package com.example.dipstore.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.text.*
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
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

