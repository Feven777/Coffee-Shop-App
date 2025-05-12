package com.example.shopapp.presentation.screens

import  androidx. compose. ui. text. font. FontWeight
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.shopApp.R

data class InventoryItem(
    val id: Int,
    val name: String,
    var quantity: Int,
    var price: Double,
    val currency: String = "ETB"
)

private val sampleInventoryItems = listOf(
    InventoryItem(1, "Coffee Beans", 20, 50.0),
    InventoryItem(2, "Milk",         15, 30.0),
    InventoryItem(3, "Sugar",        10, 10.0)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(navController: NavController) {
    var searchQuery      by remember { mutableStateOf("") }
    var inventoryItems   by remember { mutableStateOf(sampleInventoryItems) }
    var filteredItems    by remember { mutableStateOf(inventoryItems) }
    var showAddDialog    by remember { mutableStateOf(false) }
    var showEditDialog   by remember { mutableStateOf(false) }
    var selectedItem     by remember { mutableStateOf<InventoryItem?>(null) }
    var showOptionsDialog by remember { mutableStateOf(false) }

    val getNewId = { (inventoryItems.maxOfOrNull { it.id } ?: 0) + 1 }

    LaunchedEffect(searchQuery, inventoryItems) {
        filteredItems = if (searchQuery.isBlank()) inventoryItems
        else inventoryItems.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Box(Modifier.fillMaxSize()) {
        // Background
        Image(
            painter = painterResource(R.drawable.inv_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(Modifier.fillMaxSize().padding(16.dp)) {
            // Top bar with back arrow only changed:
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(Modifier.weight(1f))
                Text(
                    "Inventory",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(2f)
                )
                Spacer(Modifier.weight(1f))
            }

            // Search
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search items...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0x80FFFFFF),
                    focusedContainerColor   = Color(0x80FFFFFF),
                    unfocusedBorderColor    = Color.Transparent,
                    focusedBorderColor      = Color.Transparent
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .padding(vertical = 16.dp)
            )

            // List
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredItems) { item ->
                    InventoryItemCard(item = item) {
                        selectedItem = it
                        showOptionsDialog = true
                    }
                }
            }
        }

        // FAB
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
        ) {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color.White,
                contentColor   = Color.Black,
                modifier       = Modifier.size(56.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Item")
            }
            Spacer(Modifier.height(4.dp))
            Text("Add New Item", color = Color.White)
        }
    }

    // Item Options Dialog (Edit/Delete)
    if (showOptionsDialog && selectedItem != null) {
        AlertDialog(
            onDismissRequest = { showOptionsDialog = false },
            title   = { Text("Item Options") },
            text    = { Text("What would you like to do with ${selectedItem!!.name}?") },
            confirmButton = {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick = {
                        showOptionsDialog = false
                        showEditDialog = true
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                        Spacer(Modifier.width(8.dp))
                        Text("Edit")
                    }
                    Button(onClick = {
                        inventoryItems = inventoryItems.filter { it.id != selectedItem!!.id }
                        showOptionsDialog = false
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                        Spacer(Modifier.width(8.dp))
                        Text("Delete")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showOptionsDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Add Dialog
    if (showAddDialog) {
        ItemDialog(
            title     = "Add New Item",
            item      = InventoryItem(getNewId(), "", 0, 0.0),
            onDismiss = { showAddDialog = false },
            onConfirm = {
                inventoryItems = inventoryItems + it
                showAddDialog = false
            }
        )
    }

    // Edit Dialog
    if (showEditDialog && selectedItem != null) {
        ItemDialog(
            title     = "Edit Item",
            item      = selectedItem!!,
            onDismiss = { showEditDialog = false },
            onConfirm = {
                inventoryItems = inventoryItems.map { orig -> if (orig.id == it.id) it else orig }
                showEditDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDialog(
    title    : String,
    item     : InventoryItem,
    onDismiss: () -> Unit,
    onConfirm: (InventoryItem) -> Unit
) {
    var name          by remember { mutableStateOf(item.name) }
    var quantityText  by remember { mutableStateOf(item.quantity.toString()) }
    var priceText     by remember { mutableStateOf(item.price.toString()) }
    var nameError     by remember { mutableStateOf(false) }
    var quantityError by remember { mutableStateOf(false) }
    var priceError    by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape    = RoundedCornerShape(16.dp),
            colors   = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value        = name,
                    onValueChange = {
                        name = it
                        nameError = it.isBlank()
                    },
                    label        = { Text("Item Name") },
                    isError      = nameError,
                    supportingText = if (nameError) { { Text("Name cannot be empty") } } else null,
                    modifier     = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value         = quantityText,
                    onValueChange = {
                        quantityText = it
                        quantityError = it.toIntOrNull() == null || it.toInt() < 0
                    },
                    label         = { Text("Quantity") },
                    isError       = quantityError,
                    supportingText = if (quantityError) { { Text("Invalid quantity") } } else null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier      = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value         = priceText,
                    onValueChange = {
                        priceText = it
                        priceError = it.toDoubleOrNull() == null || it.toDouble() < 0
                    },
                    label         = { Text("Price") },
                    isError       = priceError,
                    supportingText = if (priceError) { { Text("Invalid price") } } else null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier      = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))

                Button(onClick = {
                    nameError     = name.isBlank()
                    quantityError = quantityText.toIntOrNull() == null
                    priceError    = priceText.toDoubleOrNull() == null
                    if (!nameError && !quantityError && !priceError) {
                        onConfirm(item.copy(
                            name     = name,
                            quantity = quantityText.toInt(),
                            price    = priceText.toDouble()
                        ))
                    }
                }) {
                    Text("Save")
                }
            }
        }
    }
}

@Composable
fun InventoryItemCard(item: InventoryItem, onClick: (InventoryItem) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick(item) },
        shape    = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(item.name, style = MaterialTheme.typography.titleMedium)
            Text("Quantity: ${item.quantity}")
            Text("Price: ${item.price} ${item.currency}")
        }
    }
}