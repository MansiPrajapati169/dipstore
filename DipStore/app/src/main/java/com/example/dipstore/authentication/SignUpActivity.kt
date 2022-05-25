package com.example.dipstore.authentication

import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.util.Patterns
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.dipstore.R
import com.example.dipstore.common.getSpannable
import com.example.dipstore.common.hideKeyboard
import com.example.dipstore.common.setIntent
import com.example.dipstore.databinding.ActivitySignUpBinding
import com.example.dipstore.home.NavigationActivity
import com.example.dipstore.utils.Constants.SIGN_UP_URL
import com.example.dipstore.viewModel.SignUpViewModel
import org.json.JSONObject


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private var required: String? = null
    private var invalidEmail: String? = null
    private var passwordShort: String? = null
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
        userDataValidate()
        submitDetails()
    }


    private fun setData() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.clSignUp.setOnClickListener {
            hideKeyboard()
        }

        viewModel.successResponse.observe(this) {
            binding.pbSignUp.visibility = ProgressBar.INVISIBLE
            setIntent(NavigationActivity())
        }

        viewModel.errorResponse.observe(this) {
            binding.pbSignUp.visibility = ProgressBar.INVISIBLE
            Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
        }

        supportActionBar?.hide()
        val spannable = SpannableString(binding.tvGoToSignIn.text)
        spannable.getSpannable(25,32,ContextCompat.getColor(applicationContext, R.color.button_color)) {
            setIntent(SignInActivity())
        }

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

        viewModel.email.observe(this) {
            when {
                it.toString().isEmpty() -> binding.tlEmail.error = required
                !Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString())
                    .matches() -> binding.tlEmail.error = invalidEmail
                else -> {
                    binding.tlEmail.error = null
                    binding.tlEmail.isEndIconVisible = true
                }
            }
        }

        viewModel.password.observe(this) {
            when {
                it.toString().isEmpty() -> binding.tlPassword.error = required
                it.toString().length <= 8 -> {
                    binding.tlPassword.error = passwordShort
                }
                else -> binding.tlPassword.error = null
            }
        }

        viewModel.name.observe(this) {
            when {
                it.toString().isEmpty() -> binding.tlName.error = required
                else -> {
                    binding.tlName.error = null
                    binding.tlName.isEndIconVisible = true
                }
            }
        }
    }

    private fun submitDetails() {
        binding.btnSignUp.setOnClickListener {
            when {
                binding.etEmail.text.toString().isEmpty() ->    binding.tlEmail.error = required
                binding.etPassword.text.toString().isEmpty() -> binding.tlPassword.error = required
                binding.etPassword.text.toString().length < 8 -> binding.tlPassword.error =
                    passwordShort
                binding.etName.text.toString().isEmpty() -> binding.tlName.error = required
                else -> {
                    binding.tlPassword.error = null
                    binding.tlEmail.error = null
                    binding.tlName.error = null
                    binding.pbSignUp.visibility = ProgressBar.VISIBLE
                    createUserRequest()
                }
            }
        }
    }

    private fun createUserRequest() {
        val credential = JSONObject()
        credential.put("email", binding.etEmail.text)
        credential.put("password", binding.etPassword.text)
        val url = SIGN_UP_URL
        viewModel.createUser(url,credential)
    }
}