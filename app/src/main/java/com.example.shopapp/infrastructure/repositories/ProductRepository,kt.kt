package com.example.shopapp.infrastructure.repositories

import com.example.shopapp.domain.entities.CartItem
import com.example.shopapp.domain.entities.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun addToCart(cartItem: CartItem)
}