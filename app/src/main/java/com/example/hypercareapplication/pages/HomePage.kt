package com.example.hypercareapplication.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hypercareapplication.BloodPressureRecords
import com.example.hypercareapplication.BloodPressureViewModel
import com.example.hypercareapplication.AuthViewModel
import androidx.navigation.NavController
import customRobotoFontFamily
import com.example.hypercareapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    navController: NavController,
    bloodPressureViewModel: BloodPressureViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val history by bloodPressureViewModel.history.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Homepage",
                        color = Color.White,
                        style = TextStyle(fontFamily = customRobotoFontFamily, fontSize = 20.sp)
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFFE05E64) // Set the background color
                )
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF))
                    .padding(innerPadding)
                    .padding(bottom = 60.dp)// Ensure padding is applied correctly
            ) {
                if (history.isNotEmpty()) {
                    // Display the most recent record
                    val latestRecord = history.sortedByDescending { it.timestamp }.first() // Sorting and getting the first record

                    item {
                        RecentBloodPressureCard(latestRecord)
                    }
                } else {
                    // Display image and message when there are no records
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp) // Fixed height for consistency
                                .padding(16.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                // Display the image (it will fill the available space)
                                Image(
                                    painter = painterResource(id = R.drawable.record), // Replace with your image resource
                                    contentDescription = "No Recent Record",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )

                                // Overlay the message on top of the image
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "No recent blood pressure records.",
                                        style = TextStyle(fontFamily = customRobotoFontFamily),
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // Spacer between the recent record and the next card
                item {
                    Spacer(modifier = Modifier.height(32.dp)) // Consistent spacing between cards
                }

                // Cards for each blood pressure information topic
                item {
                    PreventionCard(navController, imageResource = R.drawable.prevent)
                }

                // Spacer between cards
                item {
                    Spacer(modifier = Modifier.height(32.dp)) // Same spacing after each card
                }

                item {
                    LowerBloodPressureCard(navController, imageResource = R.drawable.lowblood)
                }

                // Spacer between cards
                item {
                    Spacer(modifier = Modifier.height(32.dp)) // Same spacing after each card
                }

                item {
                    MeasureBloodPressureCard(navController, imageResource = R.drawable.measure)
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    )
}


@Composable
fun PreventionCard(navController: NavController, imageResource: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Fixed height to ensure uniform size
            .padding(16.dp)
            .clickable { navController.navigate("prevent_high_blood_pressure") }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Prevention Card",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = "How to Prevent High Blood Pressure",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontFamily = customRobotoFontFamily),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Learn tips to prevent high blood pressure effectively.",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun LowerBloodPressureCard(navController: NavController, imageResource: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Fixed height to ensure uniform size
            .padding(16.dp)
            .clickable { navController.navigate("lower_blood_pressure_quickly_instructions") }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Lower Blood Pressure Card",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = "How to Lower Blood Pressure Quickly",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontFamily = customRobotoFontFamily),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Discover quick methods to lower blood pressure.",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun MeasureBloodPressureCard(navController: NavController, imageResource: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Fixed height to ensure uniform size
            .padding(16.dp)
            .clickable { navController.navigate("blood_pressure_instructions") }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Measure Blood Pressure Card",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = "How to Measure Blood Pressure at Home",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontFamily = customRobotoFontFamily),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Follow these steps to measure blood pressure accurately.",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun RecentBloodPressureCard(record: BloodPressureRecords) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Fixed height to ensure uniform size
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.record), // Example image for this card
                contentDescription = "Card Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Recent Record",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontFamily = customRobotoFontFamily),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Systolic: ${record.systolic}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontFamily = customRobotoFontFamily),
                    fontSize = 19.sp
                )
                Text(
                    text = "Diastolic: ${record.diastolic}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontFamily = customRobotoFontFamily),
                    fontSize = 19.sp
                )
                Text(
                    text = "Result: ${record.result}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontFamily = customRobotoFontFamily),
                    fontSize = 19.sp
                )
            }
        }
    }
}



