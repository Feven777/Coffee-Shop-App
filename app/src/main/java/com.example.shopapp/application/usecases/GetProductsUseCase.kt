package com.example.shopapp.application.usecases

import com.example.shopapp.domain.entities.Product

class GetProductsUseCase {
    fun execute(): List<Product> {
        return listOf(
            Product(id = "1", name = "Coffee", price = 5.0),
            Product(id = "2", name = "Tea", price = 3.0)
        )
    }
}