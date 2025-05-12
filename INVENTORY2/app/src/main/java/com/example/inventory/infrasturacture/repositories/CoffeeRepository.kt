package com.example.inventory.infrasturacture.repositories

import com.example.inventory.domain.entities.CoffeeItem
import com.example.inventory.domain.failure.Failure
import kotlinx.coroutines.flow.Flow

interface CoffeeRepository {
    fun getCoffeeItems(): Flow<Result<List<CoffeeItem>>>
    fun searchCoffeeItems(query: String): Flow<Result<List<CoffeeItem>>>
    suspend fun addCoffeeItem(coffeeItem: CoffeeItem): Result<Unit>
    suspend fun updateCoffeeItem(coffeeItem: CoffeeItem): Result<Unit>
    suspend fun deleteCoffeeItem(id: String): Result<Unit>
}

typealias Result<T> = kotlin.Result<T>
