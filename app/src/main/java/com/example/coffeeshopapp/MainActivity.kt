package com.example.coffeeshopapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Density
import com.example.coffeeshopapp.ui.theme.CoffeeShopAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CoffeeShopAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegistrationScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun RegistrationScreen(modifier: Modifier = Modifier) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(modifier = modifier.fillMaxSize()) {
        // Top Background Image (Full Screen)
        Image(
            painter = painterResource(id = R.drawable.coffee_beans2),
            contentDescription = "Top Background",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        // Bottom Background Image (Full Screen)
        Image(
            painter = painterResource(id = R.drawable.coffee_beans),
            contentDescription = "Bottom Background",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.Crop
        )

        // Middle Section with Curved Top and Bottom (x³-like shape)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clip(CurvedShape()) // Apply the new curved shape
                .padding(24.dp)
                .align(Alignment.Center)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Hello, Register here to get started",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Full Name") },
                    keyboardOptions = KeyboardOptions.Default,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (fullName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                            Toast.makeText(context, "Registration Successful!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF795548), contentColor = Color.White) // Brown color with white text
                ) {
                    Text("Register")
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = { /* Navigate to Login screen */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Already have an account? Login",
                        color = Color.Black
                    )
                }
            }
        }
    }
}

// ✅ Custom Shape for Middle Section (Curved x³-like top and bottom)
class CurvedShape : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: Density
    ): androidx.compose.ui.graphics.Outline {
        val path = Path().apply {
            moveTo(0f, 0f)

            // Cubic curve for the top (x³-like shape)
            cubicTo(
                size.width * 0.25f, -size.height * 0.3f, // First control point
                size.width * 0.75f, -size.height * 0.3f, // Second control point
                size.width, 0f // End point
            )

            lineTo(size.width, size.height)

            // Cubic curve for the bottom (x³-like shape)
            cubicTo(
                size.width * 0.75f, size.height * 1.3f, // First control point for bottom curve
                size.width * 0.25f, size.height * 1.3f, // Second control point for bottom curve
                0f, size.height // End point
            )

            close()
        }

        return androidx.compose.ui.graphics.Outline.Generic(path)
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    CoffeeShopAppTheme {
        RegistrationScreen()
    }
}