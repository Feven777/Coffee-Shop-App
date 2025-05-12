package com.example.shopapp.infrastracture.repositories


import android.content.Context
import android.util.Log
import com.example.shopapp.infrastracture.datasources.remote.AuthService
import com.example.shopapp.infrastracture.datasources.remote.RetrofitClient
import com.example.shopapp.infrastracture.dtos.AuthResponse
import com.example.shopapp.infrastracture.dtos.LoginRequest
import com.example.shopapp.infrastracture.dtos.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class AuthRepository(private val context: Context) {
    private val authService: AuthService = RetrofitClient.create(AuthService::class.java, context)

    suspend fun registerUser(name: String, email: String, password: String): AuthResponse {
        Log.d("AuthRepository", "🚀 Sending registration request...")

        val response = authService.register(RegisterRequest(name, email, password))

        Log.d("AuthRepository", "✅ API Response Code: ${response.code()}, Raw Body: ${response.body()}")

        if (!response.isSuccessful) {
            Log.e("AuthRepository", "❌ Registration failed: ${response.errorBody()?.string()}")
            throw Exception("Registration failed: ${response.errorBody()?.string()}")
        }

        return response.body()!!
    }



    suspend fun loginUser(email: String, password: String): AuthResponse {
        return withContext(Dispatchers.IO) {
            val response: Response<AuthResponse> = authService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Login failed")
            } else {
                throw Exception("Login failed: ${response.message()}")
            }
        }
    }

}