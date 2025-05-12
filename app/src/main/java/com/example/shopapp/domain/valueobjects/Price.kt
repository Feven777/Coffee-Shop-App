package com.example.inventory.domain.valueobjects


data class Price(val amount: Double, val currency: String = "ETB") {
    override fun toString(): String = "ETB%.2f".format(amount)
}
