package com.example.shopapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shopApp.R
import com.example.shopapp.infrasturacture.dtos.ShiftDto
import com.example.shopapp.presentation.viewmodels.ShiftViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftsScreen(navController: androidx.navigation.NavController) {
    val viewModel: ShiftViewModel = viewModel()

    // State variables to control visibility
    var showShifts by remember { mutableStateOf(false) }
    var showAddShift by remember { mutableStateOf(false) }
    var showUpdateShift by remember { mutableStateOf(false) }
    var showDeleteShift by remember { mutableStateOf(false) }

    // Form state variables
    var shiftType by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var employeeId by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var shiftIdToDelete by remember { mutableStateOf("") }

    // Get shifts from viewModel
    val shifts = viewModel.getShifts()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Shift Management",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Background image
            Image(
                painter = painterResource(id = R.drawable.coffee_background),
                contentDescription = "Coffee Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Shift Management",
                    fontSize = 32.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Cursive,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 24.dp)
                )

                // View Shifts Button
                ShiftActionButton(
                    text = if (showShifts) "Hide Shifts" else "View Shifts",
                    icon = Icons.AutoMirrored.Filled.List,
                    onClick = { showShifts = !showShifts }
                )

                // Display shifts if showShifts is true
                if (showShifts) {
                    shifts.forEach { shift ->
                        Text(
                            text = "${shift.shiftType} - ${shift.date} - ${shift.startTime} - ${shift.endTime}",
                            modifier = Modifier.padding(vertical = 4.dp),
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Add Shift Button
                ShiftActionButton(
                    text = if (showAddShift) "Hide Add Shift" else "Add Shift",
                    icon = Icons.Default.Add,
                    onClick = {
                        showAddShift = !showAddShift
                        if (showAddShift) {
                            showUpdateShift = false
                            showDeleteShift = false
                        }
                    }
                )

                // Add Shift Form
                if (showAddShift) {
                    ShiftForm(
                        shiftType = shiftType,
                        date = date,
                        employeeId = employeeId,
                        startTime = startTime,
                        endTime = endTime,
                        onShiftTypeChange = { shiftType = it },
                        onDateChange = { date = it },
                        onEmployeeIdChange = { employeeId = it },
                        onStartTimeChange = { startTime = it },
                        onEndTimeChange = { endTime = it }
                    )

                    Button(
                        onClick = {
                            viewModel.addShift(shiftType, date, employeeId, startTime, endTime)
                            // Reset form
                            shiftType = ""
                            date = ""
                            employeeId = ""
                            startTime = ""
                            endTime = ""
                            showAddShift = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF673AB7)
                        )
                    ) {
                        Text("Confirm add Shift")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Update Shift Button
                ShiftActionButton(
                    text = if (showUpdateShift) "Hide Update Shift" else "Update Shift",
                    icon = Icons.Default.Edit,
                    onClick = {
                        showUpdateShift = !showUpdateShift
                        if (showUpdateShift) {
                            showAddShift = false
                            showDeleteShift = false
                        }
                    }
                )

                // Update Shift Form
                if (showUpdateShift) {
                    ShiftForm(
                        shiftType = shiftType,
                        date = date,
                        employeeId = employeeId,
                        startTime = startTime,
                        endTime = endTime,
                        onShiftTypeChange = { shiftType = it },
                        onDateChange = { date = it },
                        onEmployeeIdChange = { employeeId = it },
                        onStartTimeChange = { startTime = it },
                        onEndTimeChange = { endTime = it }
                    )

                    Button(
                        onClick = {
                            // Implement update functionality
                            showUpdateShift = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF673AB7)
                        )
                    ) {
                        Text("Confirm update Shift")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Delete Shift Button
                ShiftActionButton(
                    text = if (showDeleteShift) "Hide Delete Shift" else "Delete Shift",
                    icon = Icons.Default.Delete,
                    onClick = {
                        showDeleteShift = !showDeleteShift
                        if (showDeleteShift) {
                            showAddShift = false
                            showUpdateShift = false
                        }
                    }
                )

                // Delete Shift Form
                if (showDeleteShift) {
                    OutlinedTextField(
                        value = shiftIdToDelete,
                        onValueChange = { shiftIdToDelete = it },
                        label = { Text("Shift ID to Delete") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(Color(0xFFF3E5F5), RoundedCornerShape(4.dp)),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Button(
                        onClick = {
                            shiftIdToDelete.toIntOrNull()?.let { id ->
                                viewModel.deleteShift(id)
                            }
                            shiftIdToDelete = ""
                            showDeleteShift = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF673AB7)
                        )
                    ) {
                        Text("Confirm delete Shift")
                    }
                }
            }
        }
    }
}

@Composable
fun ShiftActionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftForm(
    shiftType: String,
    date: String,
    employeeId: String,
    startTime: String,
    endTime: String,
    onShiftTypeChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onEmployeeIdChange: (String) -> Unit,
    onStartTimeChange: (String) -> Unit,
    onEndTimeChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        OutlinedTextField(
            value = shiftType,
            onValueChange = onShiftTypeChange,
            label = { Text("Shift Type") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color(0xFFF3E5F5), RoundedCornerShape(4.dp))
        )

        OutlinedTextField(
            value = date,
            onValueChange = onDateChange,
            label = { Text("Date") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color(0xFFF3E5F5), RoundedCornerShape(4.dp))
        )

        OutlinedTextField(
            value = employeeId,
            onValueChange = onEmployeeIdChange,
            label = { Text("Employee ID") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color(0xFFF3E5F5), RoundedCornerShape(4.dp))
        )

        OutlinedTextField(
            value = startTime,
            onValueChange = onStartTimeChange,
            label = { Text("Start Time") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color(0xFFF3E5F5), RoundedCornerShape(4.dp))
        )

        OutlinedTextField(
            value = endTime,
            onValueChange = onEndTimeChange,
            label = { Text("End Time") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color(0xFFF3E5F5), RoundedCornerShape(4.dp))
        )
    }
}