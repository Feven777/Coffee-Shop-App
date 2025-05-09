package com.example.coffeeshopapp.data.repository

import com.example.shopapp.infrastracture.datasources.remote.SalesApi
import com.example.shopapp.application.entities.SalesData

class SalesRepositoryImpl : SalesRepository {
    override suspend fun getSalesData(): SalesData {
        return SalesApi.fetchSalesData()
    }
}