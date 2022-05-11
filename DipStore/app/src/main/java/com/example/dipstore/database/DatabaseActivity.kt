package com.example.dipstore.database

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.example.dipstore.common.emptyValidation
import com.example.dipstore.database.StudentDatabase.Companion.getInstance
import com.example.dipstore.databinding.ActivityDatabaseBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatabaseActivity : AppCompatActivity() {
    private var result: Long = 0
    private lateinit var arrayAdapter: ArrayAdapter<String>
    lateinit var binding: ActivityDatabaseBinding
    lateinit var sdata: List<StudentData>
    lateinit var listStudent: LiveData<List<StudentData>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatabaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
    }

    private fun setData() {

        listStudent = getInstance(this).studentDao().getStudentData()
        binding.btnAdd.setOnClickListener {
            if (binding.etID.emptyValidation() || binding.etName.emptyValidation() ) {
                Toast.makeText(this@DatabaseActivity, "required", Toast.LENGTH_SHORT).show()
            } else {
                operations("Add")
            }
        }

        binding.btnUpdate.setOnClickListener {
            if (binding.etID.emptyValidation() || binding.etName.emptyValidation() ) {
                Toast.makeText(this@DatabaseActivity, "required", Toast.LENGTH_SHORT).show()
            } else {
                operations("Update")
            }
        }

        listStudent.observe(this) { students ->
            sdata = students
            setListData(students)
            binding.lvData.adapter = arrayAdapter
        }

        binding.lvData.setOnItemClickListener { adapterView, view, i, l ->
            lifecycleScope.launch(Dispatchers.IO) {
                val result = getInstance(this@DatabaseActivity).studentDao().delete(sdata[i].uid)
                withContext(Dispatchers.Main) {
                    updateData(result.toLong(),"Delete")
                }
            }
        }
    }

    private fun getData(): StudentData {
        val name = binding.etName.text.toString().trim()
        val id = binding.etID.text.toString().toInt()
        return StudentData(name, id)
    }

    private fun setListData(students: List<StudentData>) {
        val student = arrayListOf<String>()
        students.forEach {
            student.add("${it.uid} ${it.name}")
        }
        arrayAdapter = ArrayAdapter(this@DatabaseActivity, android.R.layout.simple_list_item_1, student)
    }

    private fun operations(operation: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val data = getData()
            val studentDao = getInstance(this@DatabaseActivity).studentDao()
            when(operation) {
                "Add" -> {
                    result = studentDao.insert(data)
                    withContext(Dispatchers.Main) {
                        updateData(result,operation)
                    }
                }
                "Update" -> {
                    result = studentDao.update(data).toLong()
                    withContext(Dispatchers.Main) {
                        updateData(result,operation)
                    }
                }
            }
        }
    }

    private fun updateData(result: Long, operation: String) {
        val msg = when (result.toInt()) {
            -1 -> "Duplicate"
            0 -> "User Not Found"
            1 -> if (operation == "Update") "Updated" else "Deleted"
            else -> "Added"
        }
        Toast.makeText(this@DatabaseActivity, msg, Toast.LENGTH_SHORT).show()
    }
}