package com.example.shopapp.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.presentation.activities.DashboardScreen
import com.example.shopapp.presentation.screens.InventoryScreen
import com.example.shopapp.presentation.screens.ShiftsScreen
import com.example.shopapp.presentation.screens.SalesScreen

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
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") { DashboardScreen(navController = navController) }
        composable("inventory") { InventoryScreen() }
        composable("shifts") { ShiftsScreen() }
        composable("sales") { SalesScreen() }
    }
}
