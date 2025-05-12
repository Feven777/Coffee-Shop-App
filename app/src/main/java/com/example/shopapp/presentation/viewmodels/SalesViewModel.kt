package com.example.shopapp.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeeshopapp.data.repository.SalesRepositoryImpl
import com.example.shopapp.domain.entities.SalesData
import com.example.shopapp.application.usecases.GetSalesDataUseCase
import kotlinx.coroutines.launch

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
