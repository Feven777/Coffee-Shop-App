package com.example.shopapp.infrasturacture.repositories

import kotlinx.coroutines.flow.Flow
import com. example. shopapp. domain. entities. CoffeeItem
interface CoffeeRepository {
    fun getCoffeeItems(): Flow<Result<List<CoffeeItem>>>
    fun searchCoffeeItems(query: String): Flow<Result<List<CoffeeItem>>>
    suspend fun addCoffeeItem(coffeeItem: CoffeeItem): Result<Unit>
    suspend fun updateCoffeeItem(coffeeItem: CoffeeItem): Result<Unit>
    suspend fun deleteCoffeeItem(id: String): Result<Unit>
}