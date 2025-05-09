package com.example.coffeeshopapp.data.repository

import com.example.shopapp.application.entities.SalesData


interface SalesRepository {
    suspend fun getSalesData(): SalesData
}