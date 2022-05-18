package com.example.dipstore.forgotpassword

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.example.dipstore.R
import com.example.dipstore.databinding.FragmentEmailBinding

class EmailFragment : Fragment() {
    private lateinit var binding: FragmentEmailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmailBinding.inflate(inflater, container, false)
        setData()
        return  binding.root
    }

    private fun setData() {
        val required = getString(R.string.txt_required)
        val invalidEmail = getString(R.string.txt_invalid_email)
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isEmpty() -> binding.tlEmail.error = required
                !Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString())
                    .matches() -> binding.tlEmail.error = invalidEmail
                else -> {
                    binding.tlEmail.error = null
                    binding.tlEmail.isEndIconVisible = true
                }
            }
        }
    }
}