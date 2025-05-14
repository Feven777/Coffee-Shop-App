package com.example.shopapp.presentation.viewmodels

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import StyledButton

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val viewModel: UserManagementViewModel = viewModel()

    StyledBackground {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Titles
            Text(
                text = "Reset Your Password",
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

            // Send Reset Link Button Space
            StyledButton(
                text = "Send Reset Link",
                onClick = { viewModel.onForgotPassword() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Navigation Buttons
            TextButton(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("forgot_password") { inclusive = true }
                    }
                }
            ) {
                Text(text = "Go Back to Login", color = Color.Black)
            }

            TextButton(onClick = { navController.popBackStack() }) {
                Text(text = "Go Back to Register", color = Color.Black)
            }
        }
    }
}
