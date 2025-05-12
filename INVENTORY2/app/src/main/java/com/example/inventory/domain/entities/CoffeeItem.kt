package com.example.inventory.domain.entities

data class CoffeeItem(
    val id: String,
    val name: String,
    val price: Double,
    val stock: Int
)