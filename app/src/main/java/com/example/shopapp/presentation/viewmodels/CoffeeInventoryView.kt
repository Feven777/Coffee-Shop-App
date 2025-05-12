package com.example.shopapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.application.usecases.AddCoffeeItemUseCase
import com.example.shopapp.application.usecases.GetCoffeeItemsUseCase
import com.example.shopapp.application.usecases.SearchCoffeeItemsUseCase
import com.example.shopapp.domain.entities.CoffeeItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.fold

class CoffeeInventoryViewModel(
    private val getCoffeeItemsUseCase: GetCoffeeItemsUseCase,
    private val searchCoffeeItemsUseCase: SearchCoffeeItemsUseCase,
    private val addCoffeeItemUseCase: AddCoffeeItemUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CoffeeInventoryUiState())
    val uiState: StateFlow<CoffeeInventoryUiState> = _uiState.asStateFlow()

    init {
        loadCoffeeItems()
    }

    fun loadCoffeeItems() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getCoffeeItemsUseCase().collect { result ->
                result.fold(
                    onSuccess = { items ->
                        _uiState.update {
                            it.copy(
                                coffeeItems = items,
                                isLoading = false,
                                error = null
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = error.message
                            )
                        }
                    }
                )
            }
        }
    }

    fun searchCoffeeItems(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, searchQuery = query) }
            searchCoffeeItemsUseCase(query).collect { result ->
                result.fold(
                    onSuccess = { items ->
                        _uiState.update {
                            it.copy(
                                coffeeItems = items,
                                isLoading = false,
                                error = null
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = error.message
                            )
                        }
                    }
                )
            }
        }
    }

    fun addCoffeeItem(name: String, price: Double, stock: Int) {
        viewModelScope.launch {
            val newItem = CoffeeItem(
                id = UUID.randomUUID().toString(),
                name = name,
                price = price,
                stock = stock
            )

            addCoffeeItemUseCase(newItem).fold(
                onSuccess = {
                    loadCoffeeItems()
                },
                onFailure = { error ->
                    _uiState.update { it.copy(error = error.message) }
                }
            )
        }
    }
}

data class CoffeeInventoryUiState(
    val coffeeItems: List<CoffeeItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)