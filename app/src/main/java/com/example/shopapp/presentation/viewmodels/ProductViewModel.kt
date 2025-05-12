package com.example.shopapp.presentation.viewmodels


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.shopapp.domain.entities.Product

class ProductViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(listOf(
        Product("Coffee", 2.5),
        Product("Tea", 1.8),
    ))
    val products: StateFlow<List<Product>> = _products

    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
    val cartItems: StateFlow<List<Product>> = _cartItems

    fun addToCart(product: Product) {
        _cartItems.value = _cartItems.value + product
    }
}
