package com.fsociety.studentmanagment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fsociety.studentmanagment.data.Student
import com.fsociety.studentmanagment.database.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateMapOf
import com.fsociety.studentmanagment.data.Attendance
import java.text.SimpleDateFormat
import java.util.*

class SchoolViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).schoolDao()

    // متغيرات لحفظ المدخلات في الحقول
    var studentName = MutableStateFlow("")
    var className = MutableStateFlow("")

    // دالة لإضافة تلميذ جديد للقاعدة
    fun addStudent() {
        val name = studentName.value.trim()
        val clazz = className.value.trim()

        if (name.isNotEmpty() && clazz.isNotEmpty()) {
            viewModelScope.launch { // ⬅️ ضروري جداً لكي يتم الإدخال في قاعدة البيانات
                try {
                    dao.insertStudent(Student(name = name, className = clazz))
                    // تفريغ حقل الاسم فقط، ونترك اسم القسم لكي يسهل إضافة تلميذ آخر لنفس القسم
                    studentName.value = ""
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    val attendanceMap = mutableStateMapOf<Long, Boolean>()

    // دالة لتسجيل الغياب لكل التلاميذ الظاهرين في القائمة
    fun saveAttendanceForClass(students: List<Student>) {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        viewModelScope.launch {
            for (student in students) {
                // إذا لم يتم تحديد التلميذ، نعتبره حاضراً كافتراضي (true) أو غائباً حسب رغبتك
                val isPresent = attendanceMap[student.id] ?: true

                val attendance = Attendance(
                    studentId = student.id,
                    date = currentDate,
                    isPresent = isPresent
                )
                dao.insertAttendance(attendance)
            }
        }
    }
}