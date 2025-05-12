package com.example.coffeeshopapp.data.repository

import com.example.shopapp.infrastructure.datasources.SalesApi
import com.example.shopapp.domain.entities.SalesData

// Make sure you have this interface defined somewhere:
// interface SalesRepository { suspend fun getSalesData(): SalesData }

class SalesRepositoryImpl : SalesRepository {
    override suspend fun getSalesData(): SalesData {
        return SalesApi.fetchSalesData()
    }
}


