package com.example.shopapp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    onRegister: (String, String, String) -> Unit,  // 🔹 Now includes name
    isLoading: Boolean,
    errorMessage: String,
    onLogin: () -> Unit
) {
    var name = remember { mutableStateOf("") }  // 🔹 Added name field
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Register", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Full Name") },  // 🔹 New input field for name
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { onRegister(name.value, email.value, password.value) },  // 🔹 Now includes name
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Register")
            }
            Button(
                onClick = { onLogin() },  // ✅ Calls the function in RegistrationActivity.kt
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Already have an account? Login")
            }


        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}