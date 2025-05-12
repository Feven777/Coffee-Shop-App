package com.example.inventory.application.usecases
import com.example.inventory.domain.entities.CoffeeItem
import com. example. inventory. infrasturacture. repositories. CoffeeRepository
import kotlinx.coroutines.flow.Flow

class SearchCoffeeItemsUseCase(private val repository: CoffeeRepository) {
    operator fun invoke(query: String): Flow<Result<List<CoffeeItem>>> =
        repository.searchCoffeeItems(query)
}

