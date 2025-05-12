package com.example.shopapp.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shopapp.presentation.ui.RegisterScreen
import com.example.shopapp.presentation.viewmodels.AuthViewModel

class RegistrationActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterScreen(
                onRegister = { name, email, password ->  // 🔹 Include name
                    authViewModel.registerUser(name, email, password, onSuccess = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    })
                },
                isLoading = authViewModel.isLoading.value,
                errorMessage = authViewModel.errorMessage.value,
                onLogin = { navigateToLogin() }


            )
        }
    }
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}

