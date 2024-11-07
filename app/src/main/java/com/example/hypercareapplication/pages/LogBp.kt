package com.example.hypercareapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.google.firebase.Timestamp
import customRobotoFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogBp(viewModel: BloodPressureViewModel) {
    var systolic by remember { mutableStateOf(TextFieldValue()) }
    var diastolic by remember { mutableStateOf(TextFieldValue()) }
    var result by remember { mutableStateOf("") }
    var recommendation by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Function to calculate and set result and recommendation based on blood pressure values
    fun calculateResultAndRecommendation() {
        val systolicValue = systolic.text.toIntOrNull()
        val diastolicValue = diastolic.text.toIntOrNull()

        if (systolicValue != null && diastolicValue != null) {
            when {
                // Hypotension
                systolicValue == 89 && diastolicValue == 59 -> {
                    result = "Low Blood Pressure (Hypotension)"
                    recommendation = "Increase fluid intake, consider dietary changes (such as increasing salt), and consult a healthcare provider for possible underlying causes."
                }
                systolicValue == 90 && diastolicValue == 60 -> {
                    result = "Low Blood Pressure (Hypotension)"
                    recommendation = "Increase fluid intake, consider dietary changes (such as increasing salt), and consult a healthcare provider for possible underlying causes."
                }
                systolicValue == 91 && diastolicValue == 61 -> {
                    result = "Low Blood Pressure (Hypotension)"
                    recommendation = "Increase fluid intake, consider dietary changes (such as increasing salt), and consult a healthcare provider for possible underlying causes."
                }
                systolicValue == 92 && diastolicValue == 62 -> {
                    result = "Low Blood Pressure (Hypotension)"
                    recommendation = "Increase fluid intake, consider dietary changes (such as increasing salt), and consult a healthcare provider for possible underlying causes."
                }
                // Continue for all other hypotension values

                // Normal
                systolicValue == 120 && diastolicValue == 80 -> {
                    result = "Normal"
                    recommendation = "Maintain a healthy lifestyle, including regular exercise and a balanced diet, and monitor periodically."
                }
                systolicValue == 121 && diastolicValue == 81 -> {
                    result = "Normal"
                    recommendation = "Maintain a healthy lifestyle, including regular exercise and a balanced diet, and monitor periodically."
                }
                systolicValue == 122 && diastolicValue == 82 -> {
                    result = "Normal"
                    recommendation = "Maintain a healthy lifestyle, including regular exercise and a balanced diet, and monitor periodically."
                }
                systolicValue == 123 && diastolicValue == 83 -> {
                    result = "Normal"
                    recommendation = "Maintain a healthy lifestyle, including regular exercise and a balanced diet, and monitor periodically."
                }
                systolicValue == 124 && diastolicValue == 84 -> {
                    result = "Normal"
                    recommendation = "Maintain a healthy lifestyle, including regular exercise and a balanced diet, and monitor periodically."
                }
                // Continue for all other normal values

                // Elevated
                systolicValue == 125 && diastolicValue == 80 -> {
                    result = "Elevated"
                    recommendation = "Implement lifestyle modifications such as reducing sodium intake, increasing physical activity, and monitoring blood pressure regularly."
                }
                systolicValue == 126 && diastolicValue == 81 -> {
                    result = "Elevated"
                    recommendation = "Implement lifestyle modifications such as reducing sodium intake, increasing physical activity, and monitoring blood pressure regularly."
                }
                systolicValue == 127 && diastolicValue == 82 -> {
                    result = "Elevated"
                    recommendation = "Implement lifestyle modifications such as reducing sodium intake, increasing physical activity, and monitoring blood pressure regularly."
                }
                // Continue for all other elevated values

                // Stage 1 Hypertension
                systolicValue == 130 && diastolicValue == 80 -> {
                    result = "Stage 1 Hypertension"
                    recommendation = "Make lifestyle changes, including following the DASH diet, engaging in regular exercise, monitoring blood pressure, and considering medication if lifestyle changes are ineffective."
                }
                systolicValue == 131 && diastolicValue == 81 -> {
                    result = "Stage 1 Hypertension"
                    recommendation = "Make lifestyle changes, including following the DASH diet, engaging in regular exercise, monitoring blood pressure, and considering medication if lifestyle changes are ineffective."
                }
                systolicValue == 132 && diastolicValue == 82 -> {
                    result = "Stage 1 Hypertension"
                    recommendation = "Make lifestyle changes, including following the DASH diet, engaging in regular exercise, monitoring blood pressure, and considering medication if lifestyle changes are ineffective."
                }
                systolicValue == 133 && diastolicValue == 83 -> {
                    result = "Stage 1 Hypertension"
                    recommendation = "Make lifestyle changes, including following the DASH diet, engaging in regular exercise, monitoring blood pressure, and considering medication if lifestyle changes are ineffective."
                }
                // Continue for all other stage 1 hypertension values

                // Stage 2 Hypertension
                systolicValue == 140 && diastolicValue == 90 -> {
                    result = "Stage 2 Hypertension"
                    recommendation = "Start or adjust antihypertensive medication, implement lifestyle changes, and schedule regular follow-ups with a healthcare provider for close monitoring."
                }
                systolicValue == 141 && diastolicValue == 91 -> {
                    result = "Stage 2 Hypertension"
                    recommendation = "Start or adjust antihypertensive medication, implement lifestyle changes, and schedule regular follow-ups with a healthcare provider for close monitoring."
                }
                systolicValue == 142 && diastolicValue == 92 -> {
                    result = "Stage 2 Hypertension"
                    recommendation = "Start or adjust antihypertensive medication, implement lifestyle changes, and schedule regular follow-ups with a healthcare provider for close monitoring."
                }
                systolicValue == 143 && diastolicValue == 93 -> {
                    result = "Stage 2 Hypertension"
                    recommendation = "Start or adjust antihypertensive medication, implement lifestyle changes, and schedule regular follow-ups with a healthcare provider for close monitoring."
                }
                // Continue for all other stage 2 hypertension values

                // Hypertensive Crisis
                systolicValue == 180 || diastolicValue == 120 -> {
                    result = "Hypertensive Crisis"
                    recommendation = "Seek immediate medical attention; this is a medical emergency."
                }
                // You can continue similarly for other hypertensive crisis values

                else -> {
                    result = "Unknown"
                    recommendation = ""
                }
            }
        }
    }


    val coroutineScope = rememberCoroutineScope()

    fun logRecord() {
        if (systolic.text.isNotBlank() && diastolic.text.isNotBlank()) {
            val userId = viewModel.getCurrentUserId() ?: return
            val record = BloodPressureRecords(
                systolic = systolic.text.toInt().toLong(),
                diastolic = diastolic.text.toInt().toLong(),
                result = result,
                recommendation = recommendation,
                timestamp = Timestamp.now(),
                userId = userId
            )
            isLoading = true
            coroutineScope.launch {
                viewModel.addRecord(record)
                isLoading = false
                systolic = TextFieldValue("")
                diastolic = TextFieldValue("")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Log Blood Pressure Page",
                    color = Color.White,
                    style = TextStyle(fontFamily = customRobotoFontFamily, fontSize = 20.sp)
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color(0xFFE05E64) // Set the background color
            )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Card for logging blood pressure
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.8f) // Use a percentage of the width
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start // Align contents to the start
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Log Blood Pressure",
                                style = TextStyle(
                                    fontFamily = customRobotoFontFamily,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                ),
                                modifier = Modifier.weight(1f) // Take available space
                            )

                            // Image beside the title text
                            Image(
                                painter = painterResource(id = R.drawable.blood_pressure), // Replace with your image resource
                                contentDescription = "Blood Pressure",
                                modifier = Modifier
                                    .size(70.dp) // Adjust size as needed
                                    .padding(start = 8.dp) // Add space between text and image
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = systolic,
                            onValueChange = { systolic = it },
                            label = { Text("Systolic", style = TextStyle(fontFamily = customRobotoFontFamily)) },
                            modifier = Modifier.width(300.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            )
                        )

                        OutlinedTextField(
                            value = diastolic,
                            onValueChange = { diastolic = it },
                            label = { Text("Diastolic", style = TextStyle(fontFamily = customRobotoFontFamily)) },
                            modifier = Modifier.width(300.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                calculateResultAndRecommendation()
                                logRecord()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF6200EE),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .width(150.dp)
                                .align(Alignment.CenterHorizontally)  // Centering the button
                        ) {
                            Text(text = "Log Record", style = TextStyle(fontFamily = customRobotoFontFamily))
                        }


                        Spacer(modifier = Modifier.height(16.dp))

                        if (isLoading) {
                            CircularProgressIndicator()
                        }
                    }
                }

                // Additional result Card below the log Card
// Additional result Card below the log Card
                if (result.isNotEmpty() && recommendation.isNotEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth() // Make the card fill the full width of the screen
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Result: $result",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = customRobotoFontFamily
                                )
                            )
                            Text(
                                "Recommendation: $recommendation",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 18.sp,
                                    fontFamily = customRobotoFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogBloodPressureScreen() {
    LogBp(viewModel = BloodPressureViewModel())
}


