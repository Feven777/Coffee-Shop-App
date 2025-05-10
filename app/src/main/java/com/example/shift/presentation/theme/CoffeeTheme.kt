package com.example.shift.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


val CoffeeBrown      = Color(0xFF8B5E3C)
val CoffeeBrownDark  = Color(0xFF5C3A21)
val CoffeeTan        = Color(0xFFD2B48C)
val BackgroundLight  = Color(0xFFF5F5F5)
val CardBackground   = Color(0xFFFFFFFF)
val AccentGreen      = Color(0xFF4CAF50)
val AccentRed        = Color(0xFFF44336)


private val LightColorScheme = lightColorScheme(
    primary         = CoffeeBrown,
    secondary       = CoffeeTan,
    background      = BackgroundLight,
    surface         = CardBackground,
    onPrimary       = Color.White,
    onSecondary     = CoffeeBrownDark,
    onBackground    = CoffeeBrownDark,
    onSurface       = CoffeeBrownDark
)

private val DarkColorScheme = darkColorScheme(
    primary         = CoffeeBrown,
    secondary       = CoffeeTan,
    background      = Color(0xFF121212),
    surface         = Color(0xFF1E1E1E),
    onPrimary       = Color.White,
    onSecondary     = CoffeeTan,
    onBackground    = Color.White,
    onSurface       = Color.White
)


val CoffeeTypography = Typography(
    headlineLarge = TextStyle(
        fontSize   = 30.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineSmall = TextStyle(
        fontSize   = 20.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleMedium = TextStyle(
        fontSize   = 16.sp,
        fontWeight = FontWeight.Medium
    ),
    bodyLarge = TextStyle(
        fontSize   = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    bodyMedium = TextStyle(
        fontSize   = 12.sp,
        fontWeight = FontWeight.Normal
    ),
    labelLarge = TextStyle(
        fontSize   = 14.sp,
        fontWeight = FontWeight.SemiBold
    )
)


val CoffeeShapes = Shapes(
    small  = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large  = RoundedCornerShape(0.dp)
)


@Composable
fun CoffeeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = CoffeeTypography,
        shapes      = CoffeeShapes,
        content     = content
    )
}