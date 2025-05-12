package com.example.inventory.infrasturacture.datasources.local

import com.example.inventory.infrasturacture.dtos.CoffeeItemDto

class CoffeeLocalDataSourceImpl : CoffeeLocalDataSource {
    // In a real app, this would use Room Database
    private val coffeeItems = mutableListOf(
        CoffeeItemDto("1", "Espresso", 43.50, 2),
        CoffeeItemDto("2", "Latte", 52.50, 15),
        CoffeeItemDto("3", "Cappuccino", 74.00, 3),
        CoffeeItemDto("4", "Mocka", 68.50, 2)
    )

    override suspend fun getCoffeeItems(): List<CoffeeItemDto> = coffeeItems

    override suspend fun getCoffeeItemById(id: String): CoffeeItemDto? =
        coffeeItems.find { it.id == id }

    override suspend fun saveCoffeeItem(coffeeItemDto: CoffeeItemDto) {
        coffeeItems.add(coffeeItemDto)
    }

    override suspend fun updateCoffeeItem(coffeeItemDto: CoffeeItemDto) {
        val index = coffeeItems.indexOfFirst { it.id == coffeeItemDto.id }
        if (index != -1) {
            coffeeItems[index] = coffeeItemDto
        }
    }

    override suspend fun deleteCoffeeItem(id: String) {
        coffeeItems.removeIf { it.id == id }
    }

    override suspend fun searchCoffeeItems(query: String): List<CoffeeItemDto> =
        coffeeItems.filter { it.name.contains(query, ignoreCase = true) }
}
