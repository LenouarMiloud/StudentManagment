package com.fsociety.studentmanagment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fsociety.studentmanagment.data.Attendance
import com.fsociety.studentmanagment.data.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface SchoolDao {
    @Insert
    suspend fun insertStudent(student: Student)

    @Query("SELECT * FROM students WHERE className = :className")
    fun getStudentsByClass(className: String): Flow<List<Student>>

    @Insert
    suspend fun insertAttendance(attendance: Attendance)

    @Query("SELECT * FROM attendance WHERE studentId = :studentId AND date = :date")
    suspend fun getAttendanceForStudent(studentId: Long, date: String): Attendance?

    // جلب كل سجلات الغياب لتلميذ معين بناءً على الـ ID الخاص به
    @Query("SELECT * FROM attendance WHERE studentId = :studentId ORDER BY date DESC")
    fun getAttendanceForStudent(studentId: Long): Flow<List<Attendance>>

    // جلب جميع الغيابات المسجلة في القاعدة
    @Query("SELECT * FROM attendance ORDER BY date DESC")
    fun getAllAttendance(): Flow<List<Attendance>>
}