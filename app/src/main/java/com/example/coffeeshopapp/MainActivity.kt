package com.example.coffeeshopapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Density
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coffeeshopapp.ui.theme.CoffeeShopAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CoffeeShopAppTheme {
                val navController = rememberNavController()
                Log.d("Debug", "MainActivity launched")

                NavHost(navController = navController, startDestination = "registration") {
                    composable("registration") { RegistrationScreen(navController) }
                    composable("login") { LoginScreen(navController) }
                    composable("forgot_password") { ForgotPasswordScreen(navController) }
                }
            }
        }
    }
}
@Composable
fun UserInputField(value: String, label: String, keyboardType: KeyboardType = KeyboardType.Text, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth()
    )
}
// Styled Registration Screen
@Composable
fun RegistrationScreen(navController: NavController) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    StyledBackground {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Register to Get Started", style = MaterialTheme.typography.titleLarge, color = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            UserInputField(value = fullName, label = "Full Name") { fullName = it }
            UserInputField(value = email, label = "Email", keyboardType = KeyboardType.Email) { email = it }
            UserInputField(value = password, label = "Password", keyboardType = KeyboardType.Password) { password = it }

            Spacer(modifier = Modifier.height(16.dp))

            StyledButton("Register") { handleRegistration(fullName, email, password, context, navController) }

            TextButton(onClick = { navController.navigate("login") { popUpTo("registration") { inclusive = true } } }) {
                Text("Already have an account? Login", color = Color.Black)
            }
        }
    }
}

// Styled Login Screen
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    StyledBackground {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Welcome Back! Login to Continue", style = MaterialTheme.typography.titleLarge, color = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            UserInputField(value = email, label = "Email", keyboardType = KeyboardType.Email) { email = it }
            UserInputField(value = password, label = "Password", keyboardType = KeyboardType.Password) { password = it }

            Spacer(modifier = Modifier.height(16.dp))

            StyledButton("Login") { handleLogin(email, password, context) }

            TextButton(onClick = { navController.navigate("forgot_password") { popUpTo("login") { inclusive = true } } }) {
                Text("Forgot Password?", color = Color.Black)
            }
        }
    }
}

// Styled Forgot Password Screen
@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    StyledBackground {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Reset Your Password", style = MaterialTheme.typography.titleLarge, color = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            UserInputField(value = email, label = "Email", keyboardType = KeyboardType.Email) { email = it }

            Spacer(modifier = Modifier.height(16.dp))

            StyledButton("Send Reset Link") { handleForgotPassword(email, context) }

            TextButton(onClick = { navController.navigate("login") { popUpTo("forgot_password") { inclusive = true } } }) {
                Text("Go Back to Login", color = Color.Black)
            }
        }
    }
}

// Handles Registration Logic
fun handleRegistration(fullName: String, email: String, password: String, context: android.content.Context, navController: NavController) {
    if (fullName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
        Log.d("Debug", "Registration successful")
        Toast.makeText(context, "Registration Successful!", Toast.LENGTH_SHORT).show()
        navController.navigate("login") { popUpTo("registration") { inclusive = true } }
    } else {
        Toast.makeText(context, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
    }
}

// Handles Login Logic
fun handleLogin(email: String, password: String, context: android.content.Context) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        Log.d("Debug", "Login successful")
        Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, "Please enter valid credentials!", Toast.LENGTH_SHORT).show()
    }
}

// Handles Forgot Password Logic
fun handleForgotPassword(email: String, context: android.content.Context) {
    if (email.isNotEmpty()) {
        Log.d("Debug", "Reset password link sent")
        Toast.makeText(context, "Reset Link Sent!", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, "Please enter your email!", Toast.LENGTH_SHORT).show()
    }
}

// Reusable Styled Background for Screens
@Composable
fun StyledBackground(content: @Composable ColumnScope.() -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.coffee_beans2),
            contentDescription = "Top Background",
            modifier = Modifier.fillMaxWidth().height(200.dp),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.coffee_beans),
            contentDescription = "Bottom Background",
            modifier = Modifier.fillMaxWidth().height(200.dp).align(Alignment.BottomCenter),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clip(CurvedShape())
                .padding(24.dp)
                .align(Alignment.Center)
        ) {
            Column(content = content)
        }
    }
}

// Reusable Styled Button
@Composable
fun StyledButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF795548), contentColor = Color.White)
    ) { Text(text) }
}

// Fixed Custom Shape
class CurvedShape : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: Density
    ): androidx.compose.ui.graphics.Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            cubicTo(size.width * 0.25f, -size.height * 0.3f, size.width * 0.75f, -size.height * 0.3f, size.width, 0f)
            lineTo(size.width, size.height)
            cubicTo(size.width * 0.75f, size.height * 1.3f, size.width * 0.25f, size.height * 1.3f, 0f, size.height)
            close()
        }
        return androidx.compose.ui.graphics.Outline.Generic(path)
    }
}