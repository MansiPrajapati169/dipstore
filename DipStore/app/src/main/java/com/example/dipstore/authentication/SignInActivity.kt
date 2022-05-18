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
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.example.dipstore.forgotpassword.ForgotPasswordActivity
import com.example.dipstore.home.NavigationActivity
import com.example.dipstore.R
import com.example.dipstore.common.*
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
        val spannable = SpannableString(binding.tvGoToSignup.text)
        spannable.getSpannable(23,30,ContextCompat.getColor(applicationContext, R.color.button_color)) {
            setIntent(SignUpActivity())
        }

        binding.apply {
            tlEmail.isEndIconVisible = false
            tvForgotPassword.setOnClickListener {
                setIntent(ForgotPasswordActivity())
            }
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