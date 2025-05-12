package com.example.shopapp.presentation.viewmodels

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import com.example.shopapp.R

@Composable
fun StyledBackground(content: @Composable ColumnScope.() -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background container
        Box(modifier = Modifier.fillMaxSize()) {
            // Top background image
            Image(
                painter = painterResource(id = R.drawable.coffee_beans2),
                contentDescription = "Top Background",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            // Bottom background image
            Image(
                painter = painterResource(id = R.drawable.coffee_beans),
                contentDescription = "Bottom Background",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.BottomCenter),
                contentScale = ContentScale.Crop
            )
        }

        // Main content box with curved shape
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clip(CurvedShape())
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0f)  // Added transparency
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }
        }
    }
}

// ... existing code ...

// Custom curved shape for content box
class CurvedShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            // First curve
            cubicTo(
                size.width * 0.25f, -size.height * 0.3f,
                size.width * 0.75f, -size.height * 0.3f,
                size.width, 0f
            )

            lineTo(size.width, size.height)

            // Second curve
            cubicTo(
                size.width * 0.75f, size.height * 1.3f,
                size.width * 0.25f, size.height * 1.3f,
                0f, size.height
            )

            close()
        }
        return Outline.Generic(path)
    }
}