package com.example.hypercareapplication.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.hypercareapplication.R

object NotificationUtils {
    private const val MEAL_CHANNEL_ID = "meal_reminder_channel"
    private const val MEAL_CHANNEL_NAME = "Meal Reminders"
    private const val MEAL_CHANNEL_DESC = "Notifications for meal reminders"

    private const val MEDICINE_CHANNEL_ID = "medicine_reminder_channel"
    private const val MEDICINE_CHANNEL_NAME = "Medicine Reminders"
    private const val MEDICINE_CHANNEL_DESC = "Notifications for medicine reminders"

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create Meal Notification Channel
            val mealImportance = NotificationManager.IMPORTANCE_HIGH
            val mealChannel = NotificationChannel(MEAL_CHANNEL_ID, MEAL_CHANNEL_NAME, mealImportance).apply {
                description = MEAL_CHANNEL_DESC
            }

            val medicineImportance = NotificationManager.IMPORTANCE_HIGH
            val medicineChannel = NotificationChannel(MEDICINE_CHANNEL_ID, MEDICINE_CHANNEL_NAME, medicineImportance).apply {
                description = MEDICINE_CHANNEL_DESC
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mealChannel)
            notificationManager.createNotificationChannel(medicineChannel)
        }
    }

    fun showMealNotification(context: Context, title: String, message: String) {
        val notificationBuilder = NotificationCompat.Builder(context, MEAL_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
        }
    }

    fun showMedicineNotification(context: Context, title: String, message: String) {
        val notificationBuilder = NotificationCompat.Builder(context, MEDICINE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
        }
    }
}
