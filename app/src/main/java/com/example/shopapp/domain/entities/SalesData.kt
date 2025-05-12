package com.example.shopapp.domain.entities

data class SalesData(
    val dailyRevenue: Double,
    val dailyRevenueTrend: Double,
    val weeklyRevenue: Double,
    val weeklyRevenueTrend: Double,
    val monthlyRevenue: Double,
    val monthlyRevenueTrend: Double,
    val averageOrderValue: Double,
    val averageOrderValueTrend: Double,
    val topSellingItems: List<TopSellingItem>
)

data class TopSellingItem(
    val productName: String,
    val unitsSold: Int,
    val revenue: Double,
    val trend: Double
)
