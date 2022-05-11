package com.example.dipstore.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StudentData::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
}