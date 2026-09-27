package com.fsociety.studentmanagment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fsociety.studentmanagment.screens.AddStudentScreen
import com.fsociety.studentmanagment.screens.AttendanceScreen
import com.fsociety.studentmanagment.screens.HomeScreen
import com.fsociety.studentmanagment.screens.ReportsScreen
import com.fsociety.studentmanagment.viewmodel.SchoolViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // تعريف الـ NavController للتنقل
            val navController = rememberNavController()
            // تعريف الـ ViewModel المشترك بين الشاشات
            val viewModel: SchoolViewModel = viewModel()

            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                NavHost(navController = navController, startDestination = "home") {

                    // الشاشة الرئيسية
                    composable("home") {
                        HomeScreen(
                            onNavigateToAddStudent = { navController.navigate("add_student") },
                            onNavigateToAttendance = { className -> navController.navigate("attendance/$className") },
                            onNavigateToReports = { navController.navigate("reports") }
                        )
                    }

                    // شاشة إضافة تلميذ
                    composable("add_student") {
                        AddStudentScreen(viewModel = viewModel)
                    }

                    // شاشة تسجيل الغياب (تستقبل اسم القسم كمتغير)
                    composable(
                        route = "attendance/{className}",
                        arguments = listOf(navArgument("className") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val className = backStackEntry.arguments?.getString("className") ?: ""
                        AttendanceScreen(viewModel = viewModel, selectedClass = className)
                    }

                    // شاشة التقارير
                    composable("reports") {
                        ReportsScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}