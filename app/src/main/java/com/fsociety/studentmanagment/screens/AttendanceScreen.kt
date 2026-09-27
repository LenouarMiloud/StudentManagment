package com.fsociety.studentmanagment.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import com.fsociety.studentmanagment.database.AppDatabase
import com.fsociety.studentmanagment.viewmodel.SchoolViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceScreen(viewModel: SchoolViewModel, selectedClass: String) {
    // جلب قائمة التلاميذ الخاصة بالقسم المحدد من القاعدة (سنحتاج لإضافة هذه الـ Flow في الـ DAO أو جلبها مباشرة)
    // للتوضيح، سنفترض أننا نمرر القائمة أو نجلبها عبر DAO:
    val dao = AppDatabase.getDatabase(viewModel.getApplication()).schoolDao()
    val students by dao.getStudentsByClass(selectedClass).collectAsState(initial = emptyList())

    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("تسجيل غيابات قسم: $selectedClass") }
            )
        },
        bottomBar = {
            // زر حفظ الغياب في الأسفل
            Surface(tonalElevation = 8.dp) {
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Button(
                        onClick = { viewModel.saveAttendanceForClass(students) },
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("حفظ غيابات تاريخ: $currentDate", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "حدد التلاميذ الغائبين:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (students.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("لا توجد تلاميذ مسجلين في هذا القسم بعد.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(students) { student ->
                        // القيمة الافتراضية: التلميذ حاضر (true)
                        val isPresent = viewModel.attendanceMap[student.id] ?: true

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = student.name, style = MaterialTheme.typography.bodyLarge)

                                // زر تبديل (Switch) أو Checkbox لتحديد هل هو حاضر أم غائب
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = if (isPresent) "حاضر" else "غائب")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Switch(
                                        checked = isPresent,
                                        onCheckedChange = { checked ->
                                            viewModel.attendanceMap[student.id] = checked
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}