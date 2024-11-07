package com.example.hypercareapplication.pages

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.app.TimePickerDialog
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import customRobotoFontFamily
import java.util.Calendar
import com.example.hypercareapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineSchedule(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val medicineScheduleViewModel: MedicineScheduleViewModel = viewModel()

    // Load medicine times when the screen is first displayed
    LaunchedEffect(Unit) {
        medicineScheduleViewModel.loadMedicineTimes(context)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)) // Background color matching the Log BP Page
    ) {
        // Top bar
        TopAppBar(
            title = {
                Text(
                    text = "Medicine Schedule Page",
                    color = Color.White,
                    style = TextStyle(fontFamily = customRobotoFontFamily, fontSize = 20.sp)
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color(0xFFE05E64) // Set the background color
            )
        )


        // Main content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Row for Text and Image
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween // Space out the items
                        ) {
                            // Text comes first, so it takes available space
                            Text(
                                "Set Medicine Times",
                                style = TextStyle(
                                    fontFamily = customRobotoFontFamily,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                ),
                                modifier = Modifier.weight(1f) // Makes the text take up remaining space
                            )

                            // Add your image here
                            Image(
                                painter = painterResource(id = R.drawable.med_sched),
                                contentDescription =     "Medicine Icon",
                                modifier = Modifier
                                    .size(78.dp) // Adjust size as needed
                                    .padding(start = 16.dp) // Space between text and image
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Medicine Time Picker for each medicine time
                        MedicineTimePicker("Medicine Time 1", medicineScheduleViewModel.medicineTimesList[0]) { time ->
                            medicineScheduleViewModel.setMedicineTime(0, time)
                            medicineScheduleViewModel.saveMedicineTimes(context)
                            scheduleMedicineNotification(context, time, "Medicine Time 1")
                            Toast.makeText(context, "Medicine Time 1 set successfully!", Toast.LENGTH_SHORT).show()
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        MedicineTimePicker("Medicine Time 2", medicineScheduleViewModel.medicineTimesList[1]) { time ->
                            medicineScheduleViewModel.setMedicineTime(1, time)
                            medicineScheduleViewModel.saveMedicineTimes(context)
                            scheduleMedicineNotification(context, time, "Medicine Time 2")
                            Toast.makeText(context, "Medicine Time 2 set successfully!", Toast.LENGTH_SHORT).show()
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        MedicineTimePicker("Medicine Time 3", medicineScheduleViewModel.medicineTimesList[2]) { time ->
                            medicineScheduleViewModel.setMedicineTime(2, time)
                            medicineScheduleViewModel.saveMedicineTimes(context)
                            scheduleMedicineNotification(context, time, "Medicine Time 3")
                            Toast.makeText(context, "Medicine Time 3 set successfully!", Toast.LENGTH_SHORT).show()
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                medicineScheduleViewModel.clearMedicineTimes(context)
                                Toast.makeText(context, "Medicine times cleared!", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF6200EE),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.width(150.dp)
                        ) {
                            Text(text = "Clear schedule", style = TextStyle(fontFamily = customRobotoFontFamily))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MedicineTimePicker(
    label: String,
    selectedTime: Calendar?,
    onTimeSelected: (Calendar) -> Unit
) {
    val context = LocalContext.current
    Button(
        onClick = {
            val calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    onTimeSelected(calendar)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            text = selectedTime?.let {
                "$label: ${it.get(Calendar.HOUR_OF_DAY)}:${String.format("%02d", it.get(Calendar.MINUTE))}"
            } ?: "Set $label"
        )
    }
}

fun scheduleMedicineNotification(context: Context, calendar: Calendar, medicineName: String) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    if (alarmManager == null) {
        Log.e("MedicineSchedule", "AlarmManager is null, cannot set alarm.")
        return
    }

    val intent = Intent(context, MedicineAlarmReceiver::class.java).apply {
        putExtra("medicine_name", medicineName)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        medicineName.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    Log.i("MedicineSchedule", "Setting alarm for $medicineName at ${calendar.timeInMillis}")

    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
        Log.i("MedicineSchedule", "$medicineName reminder set successfully.")
    } catch (e: SecurityException) {
        Log.e("MedicineSchedule", "SecurityException: Failed to set reminder for $medicineName: ${e.message}")
    } catch (e: Exception) {
        Log.e("MedicineSchedule", "Exception: Failed to set reminder for $medicineName: ${e.message}")
    }
}
