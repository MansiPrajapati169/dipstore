package com.example.dipstore.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StudentData(
    val name: String,
    @PrimaryKey
    val uid: Int
)
