package com.example.shopapp.infrasturacture.dtos

import com.example.shopapp.domain.entities.CoffeeItem

data class CoffeeItemDto(
    val id: String,
    val name: String,
    val price: Double,
    val stock: Int
) {
    fun toDomain(): CoffeeItem = CoffeeItem(id, name, price, stock)
}
