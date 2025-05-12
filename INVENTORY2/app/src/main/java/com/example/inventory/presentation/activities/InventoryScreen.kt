package com.example.inventory.presentation.activities
import androidx. compose. foundation. clickable
import com.example.inventory.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

data class InventoryItem(
    val id: Int,
    val name: String,
    var quantity: Int,
    var price: Double,
    val currency: String = "ETB"
)

val sampleInventoryItems = listOf(
    InventoryItem(1, "Coffee Beans", 20, 50.0),
    InventoryItem(2, "Milk", 15, 30.0),
    InventoryItem(3, "Sugar", 10, 10.0)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    modifier: Modifier = Modifier,
    onAddItemClick: () -> Unit,
    onItemClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var inventoryItems by remember { mutableStateOf(sampleInventoryItems) }
    var filteredItems by remember { mutableStateOf(listOf<InventoryItem>()) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<InventoryItem?>(null) }
    var showOptionsDialog by remember { mutableStateOf(false) }

    val getNewId = { (inventoryItems.maxOfOrNull { it.id } ?: 0) + 1 }

    LaunchedEffect(searchQuery, inventoryItems) {
        filteredItems = if (searchQuery.isBlank()) {
            inventoryItems
        } else {
            inventoryItems.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.inv_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                IconButton(
                    onClick = onAddItemClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Inventory",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(2f)
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search items...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0x80FFFFFF),
                    focusedContainerColor = Color(0x80FFFFFF),
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedPlaceholderColor = Color.DarkGray,
                    focusedPlaceholderColor = Color.DarkGray,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .padding(vertical = 16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredItems) { item ->
                    InventoryItemCard(
                        item = item,
                        onClick = {
                            selectedItem = item
                            showOptionsDialog = true
                        }
                    )
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color.White,
                contentColor = Color.Black,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Item",
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Add New Item",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    if (showOptionsDialog && selectedItem != null) {
        AlertDialog(
            onDismissRequest = { showOptionsDialog = false },
            title = { Text("Item Options") },
            text = { Text("What would you like to do with ${selectedItem?.name}?") },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            showOptionsDialog = false
                            showEditDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Edit")
                    }

                    Button(
                        onClick = {
                            selectedItem?.let { item ->
                                inventoryItems = inventoryItems.filter { it.id != item.id }
                            }
                            showOptionsDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Delete")
                    }
                }
            },
            containerColor = Color.White
        )
    }

    if (showAddDialog) {
        ItemDialog(
            title = "Add New Item",
            item = InventoryItem(id = getNewId(), name = "", quantity = 0, price = 0.0),
            onDismiss = { showAddDialog = false },
            onConfirm = { newItem ->
                inventoryItems = inventoryItems + newItem
                showAddDialog = false
            }
        )
    }

    if (showEditDialog && selectedItem != null) {
        ItemDialog(
            title = "Edit Item",
            item = selectedItem!!,
            onDismiss = { showEditDialog = false },
            onConfirm = { updatedItem ->
                inventoryItems = inventoryItems.map {
                    if (it.id == updatedItem.id) updatedItem else it
                }
                showEditDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDialog(
    title: String,
    item: InventoryItem,
    onDismiss: () -> Unit,
    onConfirm: (InventoryItem) -> Unit
) {
    var name by remember { mutableStateOf(item.name) }
    var quantityText by remember { mutableStateOf(item.quantity.toString()) }
    var priceText by remember { mutableStateOf(item.price.toString()) }

    var nameError by remember { mutableStateOf(false) }
    var quantityError by remember { mutableStateOf(false) }
    var priceError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameError = it.isBlank()
                    },
                    label = { Text("Item Name") },
                    isError = nameError,
                    supportingText = { if (nameError) Text("Name cannot be empty") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                OutlinedTextField(
                    value = quantityText,
                    onValueChange = {
                        quantityText = it
                        val quantity = it.toIntOrNull()
                        quantityError = quantity == null || quantity < 0
                    },
                    label = { Text("Quantity") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = quantityError,
                    supportingText = { if (quantityError) Text("Enter a valid quantity") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                OutlinedTextField(
                    value = priceText,
                    onValueChange = {
                        priceText = it
                        val price = it.toDoubleOrNull()
                        priceError = price == null || price < 0
                    },
                    label = { Text("Price") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = priceError,
                    supportingText = { if (priceError) Text("Enter a valid price") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                Button(
                    onClick = {
                        nameError = name.isBlank()
                        quantityError = quantityText.toIntOrNull() == null
                        priceError = priceText.toDoubleOrNull() == null

                        if (!nameError && !quantityError && !priceError) {
                            onConfirm(
                                item.copy(
                                    name = name,
                                    quantity = quantityText.toInt(),
                                    price = priceText.toDouble()
                                )
                            )
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Save")
                }
            }
        }
    }
}

// Placeholder for InventoryItemCard
@Composable
fun InventoryItemCard(item: InventoryItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Quantity: ${item.quantity}")
            Text(text = "Price: ${item.price} ${item.currency}")
        }
    }
}
