package com.example.dipstore.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StudentData::class], version = 1)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
    private var INSTANCE: StudentDatabase? = null
    fun getInstance(context: Context): StudentDatabase {
        if (INSTANCE == null) {
            synchronized(StudentDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            StudentDatabase::class.java,
            "student-db"
        ).build()
}
}