package com.example.dipstore.authentication

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.example.dipstore.forgotpassword.ForgotPasswordActivity
import com.example.dipstore.R
import com.example.dipstore.common.hideKeyboard
import com.example.dipstore.common.setIntent
import com.example.dipstore.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private var required: String? = null
    private var invalidEmail: String? = null
    private var passwordShort: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
        userDataValidate()
        submitDetails()
    }

    private fun setData() {
        binding.clSignUp.setOnClickListener {
            hideKeyboard()
        }
        supportActionBar?.hide()
        val spannable = SpannableString(binding.tvGoToSignIn.text)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                setIntent(SignInActivity())
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.typeface = Typeface.DEFAULT_BOLD
                ds.color = ContextCompat.getColor(applicationContext, R.color.button_color)
                ds.bgColor = ContextCompat.getColor(applicationContext, R.color.white)
            }
        }
        spannable.setSpan(clickableSpan, 25, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.apply {
            tlEmail.isEndIconVisible = false
            tlName.isEndIconVisible = false
            tvGoToSignIn.text = spannable
            tvGoToSignIn.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun userDataValidate() {

        required = getString(R.string.txt_required)
        invalidEmail = getString(R.string.txt_invalid_email)
        passwordShort = getString(R.string.password_short_text)

        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isEmpty() -> binding.tlEmail.error = required
                !Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches() -> binding.tlEmail.error = invalidEmail
                else -> {
                    binding.tlEmail.error = null
                    binding.tlEmail.isEndIconVisible = true
                }
            }
        }

        binding.etName.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isEmpty() -> binding.tlName.error = required
                else -> {
                    binding.tlName.error = null
                    binding.tlName.isEndIconVisible = true
                }
            }
        }

        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isEmpty() -> binding.tlPassword.error = required
                text.toString().length <= 8 -> {
                    binding.tlPassword.error = passwordShort
                }
                else -> binding.tlPassword.error = null
            }
        }
    }

    private fun submitDetails() {
        binding.btnSignUp.setOnClickListener {
            when {
                binding.etEmail.text.toString().isEmpty() && binding.etPassword.text.toString().isEmpty() -> {
                    binding.tlPassword.error = required
                    binding.tlEmail.error = required
                }
                binding.etPassword.text.toString().length < 8 -> binding.tlPassword.error = passwordShort
                binding.etName.text.toString().isEmpty() -> binding.tlName.error = required
                else -> {
                    binding.tlPassword.error = null
                    binding.tlEmail.error = null
                    binding.tlName.error = null
                    setIntent(ForgotPasswordActivity())
                }
            }
        }
    }
}