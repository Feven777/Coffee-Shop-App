package com.example.coffeeshopapp.data.repository

import com.example.shopapp.domain.entities.SalesData


interface SalesRepository {
    suspend fun getSalesData(): SalesData
}