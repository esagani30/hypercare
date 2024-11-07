package com.example.hypercareapplication.pages

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationManager
import android.app.PendingIntent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.hypercareapplication.R

class MedicineAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val medicineName = intent.getStringExtra("medicine_name") ?: "Medicine Time"
        Log.i("MedicineAlarmReceiver", "Received alarm for: $medicineName")

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, "medicine_reminder_channel")
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Medicine Reminder")
            .setContentText("It's time to take $medicineName!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(medicineName.hashCode(), notification)
    }
}
