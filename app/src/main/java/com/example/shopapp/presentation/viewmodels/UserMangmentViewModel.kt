package com.example.shopapp.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.shopapp.application.usecases.ForgotPasswordUseCase
import com.example.shopapp.application.usecases.LoginUserUseCase
import com.example.shopapp.application.usecases.RegisterUserUseCase
import com.example.shopapp.domain.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserManagementViewModel(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ViewModel() {

    // State variables
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val fullName = mutableStateOf("")
    val errorMessage = mutableStateOf("")
    val registrationSuccess = mutableStateOf(false)
    val isAuthenticated = mutableStateOf(false)

    // Handles user registration
    fun onRegister() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = User(
                    email = email.value,
                    password = password.value,
                    fullName = fullName.value
                )
                registerUserUseCase.execute(user)
                errorMessage.value = "" // ✅ Clears error message on success
                registrationSuccess.value = true
                isAuthenticated.value = true
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Registration failed"
                registrationSuccess.value = false
            }
        }
    }

    private fun RegisterUserUseCase.execute(unit: Any) {}

    private fun CoroutineScope.User(
        email: kotlin.String,
        password: kotlin.String,
        fullName: kotlin.String
    ) {
    }

    // Handles user login
    fun onLogin() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = User(
                    email = email.value,
                    password = password.value,
                    fullName = "" // Empty if not needed for login
                )
                loginUserUseCase.execute(user)
                errorMessage.value = "" // ✅ Clears error message on success
                isAuthenticated.value = true
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Login failed"
                isAuthenticated.value = false
            }
        }
    }

    private fun LoginUserUseCase.execute(unit: Any) {}

    // Handles forgotten password recovery
    fun onForgotPassword() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                forgotPasswordUseCase.execute(email.value)
                errorMessage.value = "" // ✅ Clears error message on success
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Failed to send reset link"
            }
        }
    }
}
