package com.example.shopapp.domain.entities

data class CartItem(
    val productId: String,
    val productName: String,
    val price: Double,
    val quantity: Int
)