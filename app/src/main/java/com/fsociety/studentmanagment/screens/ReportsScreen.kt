package com.fsociety.studentmanagment.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import com.fsociety.studentmanagment.database.AppDatabase
import com.fsociety.studentmanagment.viewmodel.SchoolViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(viewModel: SchoolViewModel) {
    val dao = AppDatabase.getDatabase(viewModel.getApplication()).schoolDao()
    // جلب كل سجلات الغياب المحفوظة
    val attendanceList by dao.getAllAttendance().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("تقارير الغيابات المسجلة") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "سجل الحضور والغياب العام:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (attendanceList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("لا توجد سجلات غياب مسجلة حتى الآن.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(attendanceList) { record ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(text = "التاريخ: ${record.date}", style = MaterialTheme.typography.bodyMedium)
                                    Text(text = "رقم التلميذ ID: ${record.studentId}", style = MaterialTheme.typography.bodySmall)
                                }

                                // عرض الحالة بلون أو نص واضح
                                Surface(
                                    color = if (record.isPresent) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer,
                                    shape = MaterialTheme.shapes.small
                                ) {
                                    Text(
                                        text = if (record.isPresent) "حاضر" else "غائب",
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        style = MaterialTheme.typography.bodyMedium
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