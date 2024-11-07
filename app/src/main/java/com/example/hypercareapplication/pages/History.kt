package com.example.hypercareapplication.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hypercareapplication.AuthViewModel
import com.example.hypercareapplication.BloodPressureRecords
import com.example.hypercareapplication.BloodPressureViewModel
import customRobotoFontFamily
import com.example.hypercareapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun History(viewModel: BloodPressureViewModel, authViewModel: AuthViewModel, navController: NavController) {
    // Collect history as state
    val history = viewModel.history.collectAsState(initial = emptyList()).value

    // State for showing/hiding the clear history confirmation dialog
    var showClearHistoryDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Blood Pressure History",
                    color = Color.White,
                    style = TextStyle(fontFamily = customRobotoFontFamily, fontSize = 20.sp)
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color(0xFFE05E64) // Set the background color
            ),
            actions = {
                IconButton(onClick = {
                    authViewModel.signout() // Perform signout
                    navController.navigate("login") { // Navigate to Login Screen
                        popUpTo("login") { inclusive = true } // Clear navigation stack
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.logout), // Replace with a proper icon for sign-out
                        contentDescription = "Sign Out",
                        tint = Color.Black
                    )
                }
            },
            modifier = Modifier
                .background(Color(0xFFE05E64))
        )

        Spacer(modifier = Modifier.height(16.dp)) // Space below the top bar

        // When there are no records, show a message
        if (history.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "No records available.",
                    style = TextStyle(
                        fontFamily = customRobotoFontFamily,
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        } else {
            // Clear History Button
            Button(
                onClick = { showClearHistoryDialog = true }, // Show confirmation dialog
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Clear History")
            }

            // Display the list of records
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                itemsIndexed(history.sortedByDescending { it.timestamp }) { index, record ->
                    // Check if this is the last item
                    val isLastItem = index == history.size - 1
                    RecordItem(record, isLastItem)
                }
            }
        }
    }

    // Clear History Confirmation Dialog
    if (showClearHistoryDialog) {
        AlertDialog(
            onDismissRequest = { showClearHistoryDialog = false },
            title = {
                Text(text = "Clear History")
            },
            text = {
                Text("Are you sure you want to clear your history?")
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.clearHistory() // Clear the history if confirmed
                    showClearHistoryDialog = false
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showClearHistoryDialog = false // Close the dialog without doing anything
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun RecordItem(record: BloodPressureRecords, isLastItem: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Systolic: ${record.systolic}",
                style = TextStyle(fontFamily = customRobotoFontFamily, fontSize = 16.sp, color = Color.Black)
            )
            Text(
                text = "Diastolic: ${record.diastolic}",
                style = TextStyle(fontFamily = customRobotoFontFamily, fontSize = 16.sp, color = Color.Black)
            )
            Text(
                text = "Result: ${record.result}",
                style = TextStyle(fontFamily = customRobotoFontFamily, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            )
            Text(
                text = "Recommendation: ${record.recommendation}",
                style = TextStyle(fontFamily = customRobotoFontFamily, fontSize = 16.sp, color = Color.Black)
            )
            record.timestamp?.let {
                Text(
                    text = "Timestamp: ${it.toDate()}",
                    style = TextStyle(fontFamily = customRobotoFontFamily, fontSize = 16.sp, color = Color.Black)
                )
            }
            }

        }

    // Add spacer only for the last item
    if (isLastItem) {
        Spacer(modifier = Modifier.height(80.dp))
    }
}

