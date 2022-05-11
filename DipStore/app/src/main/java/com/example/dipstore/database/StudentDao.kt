package com.example.dipstore.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(studentData: StudentData): Long

    @Update
    fun update(studentData: StudentData): Int

    @Query("delete from StudentData where uid == :id")
    fun delete(id: Int): Int

    @Query("select * from StudentData")
    fun getStudentData(): LiveData<List<StudentData>>
}