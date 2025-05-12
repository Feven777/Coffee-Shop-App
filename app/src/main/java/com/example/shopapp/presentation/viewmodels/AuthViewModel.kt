package com.example.shopapp.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.application.usecases.LoginUserUseCase
import com.example.shopapp.application.usecases.RegisterUserUseCase
import com.example.shopapp.infrastracture.datasources.local.SettingsDataStore
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")
    var userRole = mutableStateOf<String?>(null)  // ✅ Track user role

    private val registerUseCase = RegisterUserUseCase(application)
    private val loginUseCase = LoginUserUseCase(application)

    fun loginUser(email: String, password: String, onSuccess: () -> Unit) {
        isLoading.value = true
        errorMessage.value = ""

        viewModelScope.launch {
            runCatching {
                val response = loginUseCase(email, password)
                Log.d("AuthViewModel", "Backend response: Token = ${response.token}, Role = ${response.role}")
                // ✅ Store token & role
                SettingsDataStore.saveToken(getApplication(), response.token)
                SettingsDataStore.saveUserRole(getApplication(), response.role)

                userRole.value = response.role  // ✅ Update UI state

                onSuccess()
            }.onFailure { e ->
                handleError("Login failed: ${e.message}")
            }
            isLoading.value = false
        }
    }

    fun registerUser(name: String, email: String, password: String, onSuccess: () -> Unit) {
        isLoading.value = true
        errorMessage.value = ""

        Log.d("AuthViewModel", "🚀 registerUser() function called for $name")

        viewModelScope.launch {
            runCatching {
                val response = registerUseCase(name, email, password)

                Log.d("AuthViewModel", "✅ Backend response: Token = ${response.token}, Role = ${response.role}")

                SettingsDataStore.saveToken(getApplication(), response.token)
                SettingsDataStore.saveUserRole(getApplication(), response.role)

                userRole.value = response.role

                onSuccess()
            }.onFailure { e ->
                errorMessage.value = handleException(e)
                Log.e("AuthViewModel", "❌ Registration failed: ${e.message}")
            }
            isLoading.value = false
        }
    }

    // ✅ Centralized error handling
    private fun handleError(message: String) {
        Log.e("AuthViewModel", message)
        errorMessage.value = message
    }
    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            try {
                SettingsDataStore.clearToken(getApplication())  // Ensure token is removed
                SettingsDataStore.clearUserRole(getApplication())  // Ensure role is removed
                Log.d("AuthViewModel", "✅ Token & Role cleared when app closes")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "❌ Error clearing token/role: ${e.message}")
            }
        }
    }
    fun logoutUser(onLogout: () -> Unit) {
        viewModelScope.launch {
            try {
                SettingsDataStore.clearToken(getApplication())  // Remove stored token
                SettingsDataStore.clearUserRole(getApplication())  // Remove stored role
                userRole.value = null  // Reset role state
                Log.d("AuthViewModel", "✅ User logged out")
                onLogout()
            } catch (e: Exception) {
                Log.e("AuthViewModel", "❌ Error during logout: ${e.message}")
            }
        }
    }
    private fun handleException(e: Throwable): String {
        return when (e) {
            is IOException -> "Network error: Please check your internet connection."
            is HttpException -> "Server error: ${e.code()} - ${e.message()}"
            else -> "Something went wrong: ${e.message}"
        }
    }



}