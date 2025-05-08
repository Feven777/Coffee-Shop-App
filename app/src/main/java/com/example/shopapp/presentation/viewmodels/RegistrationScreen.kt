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

// Ensure imports are correct if components are in a separate package
import com.example.shopapp.presentation.viewmodels.StyledBackground
import com.example.shopapp.presentation.viewmodels.StyledButton
import com.example.shopapp.presentation.viewmodels.UserInputField

@Composable
fun RegistrationScreen(navController: NavController) {
    val viewModel: UserManagementViewModel = viewModel()
    val registrationSuccess = viewModel.registrationSuccess.value
    val errorMessage = viewModel.errorMessage.value

    StyledBackground {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Title
            Text(
                text = "Register to Get Started",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Full Name Input Field
            UserInputField(
                value = viewModel.fullName.value,
                label = "Full Name",
                onValueChange = { viewModel.fullName.value = it }
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

            // Register Button
            StyledButton(text = "Register") {
                viewModel.onRegister()
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display Success Message & Navigate on Success
            if (registrationSuccess) {
                Text(
                    text = "Registration Successful! Redirecting...",
                    color = Color.Green
                )

                LaunchedEffect(Unit) {
                    navController.navigate("home")
                }
            }

            // Display Error Message if Registration Fails
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigate to Login Screen
            TextButton(onClick = { navController.navigate("login") }) {
                Text(text = "Already have an account? Login", color = Color.Black)
            }
        }
    }
}