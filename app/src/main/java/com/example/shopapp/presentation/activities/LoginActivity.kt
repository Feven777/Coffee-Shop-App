package com.example.shopapp.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.shopapp.presentation.ui.LoginScreen
import com.example.shopapp.presentation.viewmodels.AuthViewModel

class LoginActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                onLogin = { email, password ->
                    authViewModel.loginUser(email, password, onSuccess = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish() // optional: prevent back navigation to login
                    })
                },
                isLoading = authViewModel.isLoading.value,
                errorMessage = authViewModel.errorMessage.value
            )
        }
    }
}
