package com.example.dipstore.authentication

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.example.dipstore.forgotpassword.ForgotPasswordActivity
import com.example.dipstore.home.NavigationActivity
import com.example.dipstore.R
import com.example.dipstore.common.hideKeyboard
import com.example.dipstore.common.setIntent
import com.example.dipstore.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private var required : String? = null
    private var invalidEmail : String? = null
    private var passwordShort : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
        userDataValidate()
        submitDetails()
    }

    private fun setData() {
        binding.clSignIn.setOnClickListener {
            hideKeyboard()
        }
        supportActionBar?.hide()
        binding.tlEmail.isEndIconVisible = false
        val spannable = SpannableString( binding.tvGoToSignup.text)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                setIntent(SignUpActivity())
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.typeface = Typeface.DEFAULT_BOLD
                ds.color = ContextCompat.getColor(applicationContext, R.color.button_color)
                ds.bgColor = ContextCompat.getColor(applicationContext, R.color.white)
            }
        }

        binding.tvForgotPassword.setOnClickListener {
           setIntent(ForgotPasswordActivity())
        }

        spannable.setSpan(clickableSpan, 23, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.apply {
            tvGoToSignup.text = spannable
            tvGoToSignup.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun userDataValidate() {

        required = getString(R.string.txt_required)
        invalidEmail = getString(R.string.txt_invalid_email)
        passwordShort = getString(R.string.password_short_text)

        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isEmpty() -> binding.tlEmail.error = required
                !Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()-> binding.tlEmail.error = invalidEmail
                else -> { binding.tlEmail.error = null
                binding.tlEmail.isEndIconVisible = true}
            }
        }

        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isEmpty() ->  binding.tlPassword.error = required
                text.toString().length <= 8 -> { binding.tlPassword.error = passwordShort }
                else ->  binding.tlPassword.error = null
            }
        }
    }

    private fun submitDetails() {
        binding.btnSignIn.setOnClickListener {
            when {
                binding.etEmail.text.toString().isEmpty()  -> binding.tlEmail.error = required
                binding.etPassword.text.toString().isEmpty() ->  binding.tlPassword.error = required
                binding.etPassword.text.toString().length < 8 ->  binding.tlPassword.error = passwordShort
                else -> {
                    binding.tlPassword.error = null
                    binding.tlEmail.error = null
                    setIntent(NavigationActivity())
                }
            }
        }
    }
}