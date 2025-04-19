package com.example.expensetracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.expensetracking.navigation.ExpenseTrackingNavHost
import com.example.expensetracking.ui.theme.ExpenseTrackingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackingTheme {
                val navController = rememberNavController()

                ExpenseTrackingNavHost(navController = navController)
            }
        }
    }
}
