package com.example.shopapp.presentation.viewmodels

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shopapp.domain.entities.Product

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: ProductViewModel = viewModel()
    val products = viewModel.products.collectAsState().value
    val cartItems = viewModel.cartItems.collectAsState().value

    StyledBackground {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Title
            Text(
                text = "Products",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Product List
            LazyColumn {
                items(products) { product ->
                    ProductItem(
                        product = product,
                        onAddToCart = { viewModel.addToCart(product) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Cart Summary
            Text(
                text = "Cart: ${cartItems.size} items",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}

@Composable
fun ProductItem(product: Product, onAddToCart: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Product Name
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyLarge
                )

                // Product Price
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Add to Cart Button
            StyledButton(text = "Add to Cart", onClick = onAddToCart)
        }
    }
}