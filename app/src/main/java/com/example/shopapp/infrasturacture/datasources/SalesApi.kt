package com.example.shopapp.infrastructure.datasources

import com.example.shopapp.domain.entities.SalesData
import com.example.shopapp.domain.entities.TopSellingItem
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
                TopSellingItem("Cappuccino", unitsSold = 180, revenue = 540.0, trend = 0.05),
                TopSellingItem("Latte",       unitsSold = 160, revenue = 480.0, trend = -0.03),
                TopSellingItem("Espresso",    unitsSold = 120, revenue = 300.0, trend = 0.08)
            )
        )
    }
}
