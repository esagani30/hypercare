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
import androidx.compose.ui.res.painterResource // Import this for image resource
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
fun MealScheduleScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val mealScheduleViewModel: MealScheduleViewModel = viewModel()

    LaunchedEffect(Unit) {
        mealScheduleViewModel.loadMealTimes(context)
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
                    text = "Meal Schedule Page",
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth() // Ensures Row uses full width
                        ) {
                            Text(
                                "Set Meal Times",
                                style = TextStyle(
                                    fontFamily = customRobotoFontFamily,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                ),
                                modifier = Modifier.weight(1f) // Pushes the image to the right
                            )
                            Spacer(modifier = Modifier.width(8.dp)) // Space between text and image
                            Image(
                                painter = painterResource(id = R.drawable.meal_sched), // Replace with your image resource
                                contentDescription = "Meal Icon",
                                modifier = Modifier.size(70.dp) // Adjust size as needed
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        MealTimePicker("Breakfast Time", mealScheduleViewModel.mealTimesList[0]) { time ->
                            mealScheduleViewModel.setMealTime(0, time)
                            mealScheduleViewModel.saveMealTimes(context)
                            scheduleMealNotification(context, time, "Breakfast Time")
                            Toast.makeText(context, "Breakfast schedule set successfully!", Toast.LENGTH_SHORT).show()
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        MealTimePicker("Lunch Time", mealScheduleViewModel.mealTimesList[1]) { time ->
                            mealScheduleViewModel.setMealTime(1, time)
                            mealScheduleViewModel.saveMealTimes(context)
                            scheduleMealNotification(context, time, "Lunch Time")
                            Toast.makeText(context, "Lunch schedule set successfully!", Toast.LENGTH_SHORT).show()
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        MealTimePicker("Dinner Time", mealScheduleViewModel.mealTimesList[2]) { time ->
                            mealScheduleViewModel.setMealTime(2, time)
                            mealScheduleViewModel.saveMealTimes(context)
                            scheduleMealNotification(context, time, "Dinner Time")
                            Toast.makeText(context, "Dinner schedule set successfully!", Toast.LENGTH_SHORT).show()
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                mealScheduleViewModel.clearMealTimes(context) // Pass context to clearMealTimes
                                Toast.makeText(context, "Meal times cleared!", Toast.LENGTH_SHORT).show()
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
fun MealTimePicker(
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
                "$label: ${it.get(Calendar.HOUR_OF_DAY)}:${it.get(Calendar.MINUTE)}"
            } ?: "Set $label"
        )
    }
}

fun scheduleMealNotification(context: Context, calendar: Calendar, mealName: String) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    if (alarmManager == null) {
        Log.e("MealSchedule", "AlarmManager is null, cannot set alarm.")
        return
    }

    val intent = Intent(context, MealAlarmReceiver::class.java).apply {
        putExtra("meal_name", mealName)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        mealName.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    Log.i("MealSchedule", "Setting alarm for $mealName at ${calendar.timeInMillis}")

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
        Log.i("MealSchedule", "$mealName reminder set successfully.")
    } catch (e: SecurityException) {
        Log.e("MealSchedule", "SecurityException: Failed to set reminder for $mealName: ${e.message}")
    } catch (e: Exception) {
        Log.e("MealSchedule", "Exception: Failed to set reminder for $mealName: ${e.message}")
    }
}
