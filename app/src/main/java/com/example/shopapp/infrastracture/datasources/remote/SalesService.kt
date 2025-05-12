package com.example.shopapp.infrastracture.datasources.remote

import com.example.shopapp.application.entities.SalesData
import retrofit2.http.GET

interface SalesService {
    @GET("/api/sales/summary")
    suspend fun getSalesSummary(): SalesData
}