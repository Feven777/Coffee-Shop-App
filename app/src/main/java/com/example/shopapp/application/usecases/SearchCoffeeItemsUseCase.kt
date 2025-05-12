package com.example.shopapp.application.usecases
import com.example.shopapp.domain.entities.CoffeeItem
import com. example. shopapp. infrasturacture. repositories. CoffeeRepository
import kotlinx.coroutines.flow.Flow

class SearchCoffeeItemsUseCase(private val repository: CoffeeRepository) {
    operator fun invoke(query: String): Flow<Result<List<CoffeeItem>>> =
        repository.searchCoffeeItems(query)
}

