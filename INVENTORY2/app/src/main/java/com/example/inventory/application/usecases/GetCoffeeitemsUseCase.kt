package com.example.inventory.application.usecases

import com.example.inventory.domain.entities.CoffeeItem
import com. example. inventory. infrasturacture. repositories. CoffeeRepository
import kotlinx.coroutines.flow.Flow

class GetCoffeeItemsUseCase(private val repository: CoffeeRepository) {
    operator fun invoke(): Flow<Result<List<CoffeeItem>>> = repository.getCoffeeItems()
}