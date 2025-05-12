package com.example.shopapp.infrastracture.repositories

import android.content.Context
import com.example.shopapp.application.entities.SalesData
import com.example.shopapp.infrastracture.datasources.remote.RetrofitClient
import com.example.shopapp.infrastracture.datasources.remote.SalesService

class SalesRepositoryImpl(private val context: Context) : SalesRepository {
    override suspend fun getSalesSummary(): SalesData {
        val salesService = RetrofitClient.create(SalesService::class.java, context)
        return salesService.getSalesSummary()
    }
}