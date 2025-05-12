package com.example.shopapp.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.domain.entities.SalesData
import com.example.shopapp.domain.entities.TopSellingItem
import com.example.shopapp.presentation.screens.InventoryScreen
import com.example.shopapp.presentation.screens.SalesScreen
import com.example.shopapp.presentation.screens.ShiftsScreen
import com.example.shopapp.presentation.ui.RegisterScreen
import com.example.shopapp.presentation.screens.LoginScreen// Corrected import here

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopAppNavigation()
        }
    }
}

@Composable
fun ShopAppNavigation() {
    val navController = rememberNavController()
    var isAuthenticated by remember { mutableStateOf(false) }

    val sampleSalesData = SalesData(
        dailyRevenue = 4800.0,
        dailyRevenueTrend = 0.08,
        weeklyRevenue = 300240.0,
        weeklyRevenueTrend = 0.12,
        monthlyRevenue = 12480.0,
        monthlyRevenueTrend = -0.03,
        averageOrderValue = 8.5,
        averageOrderValueTrend = 0.05,
        topSellingItems = listOf(
            TopSellingItem("Cappuccino", 180, 540.0, 0.05),
            TopSellingItem("Latte", 160, 480.0, -0.03),
            TopSellingItem("Espresso", 120, 300.0, 0.06),
        )
    )

    NavHost(navController = navController, startDestination = if (isAuthenticated) "dashboard" else "login") {
        // Login Screen
        composable("login") {
            val onNavigateToRegister = null
            LoginScreen(navController = navController, onLoginSuccess = {
                    isAuthenticated = true
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                }, onNavigateToRegister = onNavigateToRegister)
        }

        // Register Screen
        composable("register") {
            RegisterScreen(
                navController = navController,
                onRegisterSuccess = {
                    isAuthenticated = true
                    navController.navigate("dashboard") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        // Dashboard Screen
        composable("dashboard") {
            DashboardScreen(navController)
        }

        // Inventory Screen
        composable("inventory") {
            InventoryScreen(navController)
        }

        // Shifts Screen
        composable("shifts") {
            ShiftsScreen(navController)
        }

        // Sales Screen
        composable("sales") {
            SalesScreen(
                salesData = sampleSalesData,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
