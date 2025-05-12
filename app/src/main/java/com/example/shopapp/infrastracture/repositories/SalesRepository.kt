package com.example.shopapp.infrastracture.repositories

import com.example.shopapp.application.entities.SalesData


interface SalesRepository {
    suspend fun getSalesData(): SalesData
}