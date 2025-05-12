package com.example.shopapp.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF8D6E63), // Brown header
    secondary = Color(0xFF6D4C41), // Darker brown for accent elements
    tertiary = Color(0xFFD7CCC8), // Soft beige for highlights
    background = Color(0xFFF5F5F5), // Light beige background
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val CustomTypography = Typography(
    titleLarge = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = LightColorScheme.primary
    ),
    bodyLarge = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        color = LightColorScheme.onBackground
    ),
    labelLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = LightColorScheme.secondary
    )
)

@Composable
fun DashTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = CustomTypography,
        content = content
    )
}