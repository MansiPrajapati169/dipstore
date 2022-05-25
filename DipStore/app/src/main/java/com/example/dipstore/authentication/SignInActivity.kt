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
import androidx.databinding.DataBindingUtil
import com.example.dipstore.R
import com.example.dipstore.common.getSpannable
import com.example.dipstore.common.hideKeyboard
import com.example.dipstore.common.setIntent
import com.example.dipstore.databinding.ActivitySignInBinding
import com.example.dipstore.forgotpassword.ForgotPasswordActivity
import com.example.dipstore.home.NavigationActivity
import com.example.dipstore.utils.Constants
import com.example.dipstore.viewModel.SignInViewModel
import org.json.JSONObject

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private var required : String? = null
    private var invalidEmail : String? = null
    private var passwordShort : String? = null
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        setData()
        userDataValidate()
        submitDetails()
        setUpObserver()
    }

    private fun setUpObserver() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.successResponse.observe(this) {
            binding.pbSignIn.visibility = ProgressBar.INVISIBLE
            setIntent(NavigationActivity())
        }
        viewModel.errorResponse.observe(this) {
            binding.pbSignIn.visibility = ProgressBar.INVISIBLE
            Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
        }
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

        viewModel.email.observe(this) {
            when {
                it.toString().isEmpty() -> binding.tlEmail.error = required
                !Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()-> binding.tlEmail.error = invalidEmail
                else -> { binding.tlEmail.error = null
                    binding.tlEmail.isEndIconVisible = true}
            }
        }

        viewModel.password.observe(this) {
            when {
                it.toString().isEmpty() ->  binding.tlPassword.error = required
                it.toString().length <= 8 -> { binding.tlPassword.error = passwordShort }
                else ->  binding.tlPassword.error = null
            }
        }
    }

    private fun submitDetails() {
        binding.btnSignIn.setOnClickListener {
            when {
                binding.etEmail.text.toString().isEmpty()  -> binding.tlEmail.error = required
                binding.etPassword.text.toString().isEmpty() ->  binding.tlPassword.error = required
                //binding.etPassword.text.toString().length < 8 ->  binding.tlPassword.error = passwordShort
                else -> {
                    binding.tlPassword.error = null
                    binding.tlEmail.error = null
                    binding.pbSignIn.visibility = ProgressBar.VISIBLE
                    createUserRequest()
                }
            }
        }
    }

    private fun createUserRequest() {
        val credential = JSONObject()
        credential.put("email", binding.etEmail.text)
        credential.put("password", binding.etPassword.text)
        val url = Constants.SIGN_IN_URL
        viewModel.loginUser(url,credential)
    }
}