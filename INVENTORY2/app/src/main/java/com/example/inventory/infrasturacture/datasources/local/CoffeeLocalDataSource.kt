package com.example.inventory.infrasturacture.datasources.local

import com.example.inventory.infrasturacture.dtos.CoffeeItemDto

interface CoffeeLocalDataSource {
    suspend fun getCoffeeItems(): List<CoffeeItemDto>
    suspend fun getCoffeeItemById(id: String): CoffeeItemDto?
    suspend fun saveCoffeeItem(coffeeItemDto: CoffeeItemDto)
    suspend fun updateCoffeeItem(coffeeItemDto: CoffeeItemDto)
    suspend fun deleteCoffeeItem(id: String)
    suspend fun searchCoffeeItems(query: String): List<CoffeeItemDto>
}
