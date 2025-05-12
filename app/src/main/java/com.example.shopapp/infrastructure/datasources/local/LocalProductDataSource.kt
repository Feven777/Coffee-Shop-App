package com.example.shopapp.infrastructure.datasources.local

import com.example.shopapp.domain.entities.CartItem
import com.example.shopapp.domain.entities.Product

class LocalProductDataSource {

    private val products = listOf(
        Product(id = "1", name = "Coffee Mug", price = 9.99),
        Product(id = "2", name = "Espresso Machine", price = 99.99),
        Product(id = "3", name = "Coffee Beans", price = 14.99)
    )

    private val cart = mutableListOf<CartItem>()

    suspend fun getProducts(): List<Product> {
        return products
    }

    suspend fun addToCart(cartItem: CartItem) {
        cart.add(cartItem)
    }
}