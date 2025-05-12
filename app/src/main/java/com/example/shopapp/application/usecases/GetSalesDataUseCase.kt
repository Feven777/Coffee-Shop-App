package com.example.shopapp.application.usecases

import com.example.shopapp.application.entities.SalesData
import com.example.shopapp.infrastracture.repositories.SalesRepository

// fetch sales data.
class GetSalesDataUseCase(private val repository: SalesRepository) {
    suspend operator fun invoke(): SalesData {
        return repository.getSalesSummary()
    }
}