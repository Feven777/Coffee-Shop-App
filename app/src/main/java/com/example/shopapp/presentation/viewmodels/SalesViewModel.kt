package com.example.coffeeshopapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeeshopapp.data.repository.SalesRepositoryImpl
import com.example.shopapp.application.usecases.GetSalesDataUseCase
import com.example.shopapp.application.entities.SalesData
import kotlinx.coroutines.launch

// ViewModel managing the state of the Sales page.
class SalesViewModel : ViewModel() {

    var salesData = mutableStateOf<SalesData?>(null)
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    private val repository = SalesRepositoryImpl()
    private val getSalesDataUseCase = GetSalesDataUseCase(repository)

    fun fetchSalesData() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                salesData.value = getSalesDataUseCase()
                errorMessage.value = ""
            } catch (ex: Exception) {
                errorMessage.value = "Failed to fetch sales data."
            }
            isLoading.value = false
        }
    }
}