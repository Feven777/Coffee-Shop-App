package com.example.shopapp.presentation.viewmodels

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: UserManagementViewModel = viewModel()

    StyledBackground {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Title
            Text(
                text = "Login",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email Input Field
            UserInputField(
                value = viewModel.email.value,
                label = "Email",
                keyboardType = KeyboardType.Email,
                onValueChange = { viewModel.email.value = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input Field
            UserInputField(
                value = viewModel.password.value,
                label = "Password",
                keyboardType = KeyboardType.Password,
                onValueChange = { viewModel.password.value = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Login Button
            StyledButton(text = "Login") {
                viewModel.onLogin()
                if (viewModel.errorMessage.value.isNullOrEmpty()) { // ✅ Ensuring proper error handling
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Error Message Display
            viewModel.errorMessage.value?.let { message ->
                Text(
                    text = message,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Forgot Password Button
            TextButton(
                onClick = { navController.navigate("forgot_password") }
            ) {
                Text(text = "Forgot Password?", color = Color.Black)
            }

            // Register Button
            TextButton(
                onClick = { navController.navigate("register") }
            ) {
                Text(text = "Register", color = Color.Black)
            }
        }
    }
}