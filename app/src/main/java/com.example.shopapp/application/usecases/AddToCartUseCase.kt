package com.example.shopapp.application.usecases

import com.example.shopapp.domain.entities.Product

class AddToCartUseCase {
    fun execute(product: Product) {
        println("Added ${product.name} to cart")
    }
}