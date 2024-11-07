package com.example.hypercareapplication.pages

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationManager
import android.app.PendingIntent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.hypercareapplication.R

class MealAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val mealName = intent.getStringExtra("meal_name") ?: "Meal Time"
        Log.i("MealAlarmReceiver", "Received alarm for: $mealName")

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, "meal_reminder_channel")
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Meal Reminder")
            .setContentText("It's time for $mealName!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(mealName.hashCode(), notification)
    }
}


