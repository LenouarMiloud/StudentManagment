package com.fsociety.studentmanagment.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fsociety.studentmanagment.viewmodel.SchoolViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudentScreen(viewModel: SchoolViewModel) {
    val name by viewModel.studentName.collectAsState()
    val clazz by viewModel.className.collectAsState()
    val context = LocalContext.current // لجلب سياق التطبيق لإظهار رسالة

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("إضافة قسم وتلميذ جديد") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = clazz,
                onValueChange = { viewModel.className.value = it },
                label = { Text("اسم القسم (مثال: الأولى متوسط 1)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { viewModel.studentName.value = it },
                label = { Text("اسم التلميذ الكامل") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (name.isNotBlank() && clazz.isNotBlank()) {
                        viewModel.addStudent()
                        Toast.makeText(context, "تم إضافة التلميذ بنجاح!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "الرجاء ملء جميع الحقول", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("حفظ وإضافة التلميذ", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}