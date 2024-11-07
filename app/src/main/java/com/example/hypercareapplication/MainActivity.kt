package com.example.hypercareapplication

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.hypercareapplication.ui.theme.HypercareApplicationTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            createNotificationChannels()
        } else {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()

        checkNotificationPermission()

        val authViewModel: AuthViewModel by viewModels()
        setContent {
            HypercareApplicationTheme {
                MyAppNavigation(authViewModel = authViewModel)
            }
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                        == PackageManager.PERMISSION_GRANTED -> {
                    createNotificationChannels()
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            createNotificationChannels()
        }
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mealChannelId = "meal_reminder_channel"
            val mealChannelName = "Meal Reminders"
            val mealDescriptionText = "Notifications for meal reminders"
            val mealImportance = NotificationManager.IMPORTANCE_HIGH
            val mealChannel = NotificationChannel(mealChannelId, mealChannelName, mealImportance).apply {
                description = mealDescriptionText
            }
            val medicineChannelId = "medicine_reminder_channel"
            val medicineChannelName = "Medicine Reminders"
            val medicineDescriptionText = "Notifications for medicine reminders"
            val medicineImportance = NotificationManager.IMPORTANCE_HIGH
            val medicineChannel = NotificationChannel(medicineChannelId, medicineChannelName, medicineImportance).apply {
                description = medicineDescriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mealChannel)
            notificationManager.createNotificationChannel(medicineChannel)
        }
    }
}
