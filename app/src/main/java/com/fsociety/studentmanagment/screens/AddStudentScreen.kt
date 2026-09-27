package com.fsociety.studentmanagment.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fsociety.studentmanagment.viewmodel.SchoolViewModel
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudentScreen(viewModel: SchoolViewModel) {
    val name by viewModel.studentName.collectAsState()
    val clazz by viewModel.className.collectAsState()

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
            // حقل إدخال اسم القسم
            OutlinedTextField(
                value = clazz,
                onValueChange = { viewModel.className.value = it },
                label = { Text("اسم القسم (مثال: الأولى متوسط 1)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // حقل إدخال اسم التلميذ
            OutlinedTextField(
                value = name,
                onValueChange = { viewModel.studentName.value = it },
                label = { Text("اسم التلميذ الكامل") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // زر الحفظ
            Button(
                onClick = { viewModel.addStudent() },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("حفظ وإضافة التلميذ", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}