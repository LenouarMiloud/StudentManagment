package com.fsociety.studentmanagment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fsociety.studentmanagment.dao.SchoolDao
import com.fsociety.studentmanagment.data.Attendance
import com.fsociety.studentmanagment.data.Student

@Database(entities = [Student::class, Attendance::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun schoolDao(): SchoolDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "school_attendance_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}