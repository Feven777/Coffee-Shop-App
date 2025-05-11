package com.example.shopapp.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.domain.entities.SalesData
import com.example.shopapp.domain.entities.TopSellingItem
import com.example.shopapp.presentation.activities.DashboardScreen
import com.example.shopapp.presentation.screens.InventoryScreen
import com.example.shopapp.presentation.screens.ShiftsScreen
import com.example.shopapp.presentation.screens.SalesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ShopAppNavigation() }
    }
}

@Composable
fun ShopAppNavigation() {
    val navController = rememberNavController()

    // sample SalesData inline
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
            TopSellingItem("Cappuccino", unitsSold = 180, revenue = 540.0, trend = 0.05),
            TopSellingItem("Latte", unitsSold = 160, revenue = 480.0, trend = -0.03),
            TopSellingItem("Espresso", unitsSold = 120, revenue = 300.0, trend = 0.06),
        )
    )

    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") { DashboardScreen(navController) }
        composable("inventory") { InventoryScreen() }
        composable("shifts")    { ShiftsScreen() }
        composable("sales")     {
            SalesScreen(
                salesData = sampleSalesData,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
