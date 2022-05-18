package com.example.dipstore.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dipstore.R
import com.example.dipstore.authentication.CustomSpinnerAdapter
import com.example.dipstore.databinding.FragmentPhoneBinding


class PhoneFragment : Fragment() {
    private lateinit var binding: FragmentPhoneBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPhoneBinding.inflate(inflater, container, false)
        setData()
        return binding.root
    }

    private fun setData() {
        val images: ArrayList<Int> = arrayListOf()
        val adapter = CustomSpinnerAdapter(requireContext(), images)
        images.add(R.drawable.flag)
        images.add(R.drawable.flag)
        images.add(R.drawable.flag)
        images.add(R.drawable.flag)
        binding.flagSpinner.adapter = adapter
        binding.flagSpinner.setSelection(0)

        binding.etPhone.setOnFocusChangeListener { _, _ ->
            if (!binding.etPhone.isFocused) {
                binding.llPhone.setBackgroundResource(R.drawable.edittext_background)
            } else {
                binding.llPhone.setBackgroundResource(R.drawable.linear_layout_background)
            }
        }

        binding.btnSendCode.setOnClickListener {
            if (binding.etPhone.text.toString().length < 10 ) {
                Toast.makeText(requireContext(),R.string.txt_invalid_number,Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(),R.string.txt_sent,Toast.LENGTH_SHORT).show()
            }
        }
    }
}