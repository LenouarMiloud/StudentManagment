package com.fsociety.studentmanagment.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToAddStudent: () -> Unit,
    onNavigateToAttendance: (String) -> Unit,
    onNavigateToReports: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("تطبيق إدارة غيابات التلاميذ") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "مرحباً بك أستاذنا الفاضل",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // زر الانتقال لشاشة إضافة التلاميذ
            Button(
                onClick = onNavigateToAddStudent,
                modifier = Modifier.fillMaxWidth().height(55.dp)
            ) {
                Text("إضافة قسم وتلميذ جديد", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // زر الانتقال لشاشة تسجيل الغياب (سنمرر قسماً افتراضياً كمثال، أو يمكنك تطويره ليختار القسم)
            Button(
                onClick = { onNavigateToAttendance("أولى متوسط 1") },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("تسجيل غيابات اليوم", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // زر الانتقال لشاشة التقارير
            Button(
                onClick = onNavigateToReports,
                modifier = Modifier.fillMaxWidth().height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
            ) {
                Text("عرض التقارير والملخصات", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}