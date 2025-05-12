package com.example.shopapp.application.usecases

import com.example.shopapp.infrasturacture.repositories.CoffeeRepository
import com. example. shopapp. domain. entities. CoffeeItem
class AddCoffeeItemUseCase(private val repository: CoffeeRepository) {
    suspend operator fun invoke(coffeeItem: CoffeeItem): Result<Unit> =
        repository.addCoffeeItem(coffeeItem)
}