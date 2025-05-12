package com.example.shopapp.infrastructure.repositories

import com.example.shopapp.domain.entities.CartItem
import com.example.shopapp.domain.entities.Product
import com.example.shopapp.infrastructure.datasources.local.LocalProductDataSource

class ProductRepositoryImpl(
    private val localDataSource: LocalProductDataSource
) : ProductRepository {

    override suspend fun getProducts(): List<Product> {
        return localDataSource.getProducts()
    }

    override suspend fun addToCart(cartItem: CartItem) {
        localDataSource.addToCart(cartItem)
    }
}