package com.example.inventory.application.usecases

import com.example.inventory.infrasturacture.repositories.CoffeeRepository
import com. example. inventory. domain. entities. CoffeeItem
class AddCoffeeItemUseCase(private val repository: CoffeeRepository) {
    suspend operator fun invoke(coffeeItem: CoffeeItem): Result<Unit> =
        repository.addCoffeeItem(coffeeItem)
}