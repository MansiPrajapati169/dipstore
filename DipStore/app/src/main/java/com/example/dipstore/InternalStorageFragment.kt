package com.example.dipstore

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dipstore.databinding.FragmentInternalStorageBinding
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStreamReader


class InternalStorageFragment : Fragment() {
    private lateinit var binding: FragmentInternalStorageBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentInternalStorageBinding.inflate(inflater, container, false)
        setData()
        return binding.root
    }

    private fun setData() {
        binding.btnAdd.setOnClickListener {
            val file:String = binding.etFileName.text.toString()
            val data:String = binding.etData.text.toString()
            val fileOutputStream: FileOutputStream
            try {
                fileOutputStream = requireContext().openFileOutput(file, Context.MODE_PRIVATE)
                fileOutputStream.write(data.toByteArray())
            } catch (e: FileNotFoundException){
                e.printStackTrace()
            }
        }

        binding.btnView.setOnClickListener {
            val filename = binding.etFileName.text.toString()
            if(filename.trim()!=""){
                var fileInputStream: FileInputStream? = null
                fileInputStream = requireContext().openFileInput(filename)
                val inputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while (run {
                        text = bufferedReader.readLine()
                        text
                    } != null) {
                    stringBuilder.append(text)
                }
                binding.tvData.setText(stringBuilder.toString()).toString()
            }else{
                Toast.makeText(requireContext(),"file name cannot be blank",Toast.LENGTH_LONG).show()
            }
        }
    }

}