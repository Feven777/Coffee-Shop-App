package com.example.shopapp.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.infrastracture.repositories.SalesRepositoryImpl
import com.example.shopapp.application.usecases.GetSalesDataUseCase
import com.example.shopapp.application.entities.SalesData
import kotlinx.coroutines.launch

// ViewModel managing the state of the Sales page.
class SalesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SalesRepositoryImpl(getApplication())
    var salesData = mutableStateOf<SalesData?>(null)
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

//    private val repository = SalesRepositoryImpl()
    private val getSalesDataUseCase = GetSalesDataUseCase(repository)

    fun fetchSalesSummary() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = getSalesDataUseCase()
                salesData.value = response
                errorMessage.value = ""
                Log.d("SalesViewModel", "✅ Sales data fetched successfully: $response")
            } catch (ex: Exception) {
                errorMessage.value = "Failed to fetch sales data: ${ex.message}"
                Log.e("SalesViewModel", "❌ Error fetching sales data", ex)  // ✅ Logs actual error
            }
            isLoading.value = false
        }
    }

}