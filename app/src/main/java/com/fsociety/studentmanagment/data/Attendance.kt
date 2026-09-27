package com.fsociety.studentmanagment.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance")
data class Attendance(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentId: Long,
    val date: String, // بصيغة "YYYY-MM-DD"
    val isPresent: Boolean // true = حاضر، false = غائب
)