package com.example.shopapp.application.usecases

import com.example.shopapp.domain.entities.CoffeeItem
import com. example. shopapp. infrasturacture. repositories. CoffeeRepository
import kotlinx.coroutines.flow.Flow

class GetCoffeeItemsUseCase(private val repository: CoffeeRepository) {
    operator fun invoke(): Flow<Result<List<CoffeeItem>>> = repository.getCoffeeItems()
}