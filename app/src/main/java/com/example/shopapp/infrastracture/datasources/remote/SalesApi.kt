package com.example.shopapp.infrastracture.datasources.remote

import com.example.shopapp.application.entities.SalesData
import com.example.shopapp.application.entities.TopSellingItem
import kotlinx.coroutines.delay

object SalesApi {
    // Simulate a network call to fetch sales data.
    suspend fun fetchSalesData(): SalesData {
        delay(500) // Simulate network delay.
        return SalesData(
            dailyRevenue = 4800.0,
            dailyRevenueTrend = 0.08,
            weeklyRevenue = 300240.0,
            weeklyRevenueTrend = 0.12,
            monthlyRevenue = 12480.0,
            monthlyRevenueTrend = -0.03,
            averageOrderValue = 8.50,
            averageOrderValueTrend = 0.05,
            topSellingItems = listOf(
                TopSellingItem("Cappuccino", 180, 540.0, 0.05),
                TopSellingItem("Latte", 160, 480.0, -0.03),
                TopSellingItem("Espresso", 120, 300.0, 0.08)
            )
        )
    }
}