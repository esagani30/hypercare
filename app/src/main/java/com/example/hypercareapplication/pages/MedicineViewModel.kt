package com.example.hypercareapplication.pages

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import java.util.Calendar

class MedicineScheduleViewModel : ViewModel() {
    private var _medicineTimes: MutableList<Calendar?> = MutableList(3) { null }
    val medicineTimesList: List<Calendar?> get() = _medicineTimes

    fun setMedicineTime(index: Int, time: Calendar) {
        if (index in _medicineTimes.indices) {
            _medicineTimes[index] = time
        }
    }

    fun clearMedicineTimes(context: Context) {
        _medicineTimes.fill(null) // Clear the medicine times
        saveMedicineTimes(context) // Save the changes
        cancelMedicineAlarms(context) // Cancel any existing alarms
    }

    private fun cancelMedicineAlarms(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        if (alarmManager != null) {
            // Cancel alarms for each scheduled medicine time
            val medicineNames = listOf("Medicine Time 1", "Medicine Time 2", "Medicine Time 3")
            for (name in medicineNames) {
                val intent = Intent(context, MedicineAlarmReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    name.hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                alarmManager.cancel(pendingIntent)
            }
        }
    }


    fun saveMedicineTimes(context: Context) {
        val sharedPreferences = context.getSharedPreferences("medicine_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("medicine_time_1", _medicineTimes[0]?.timeInMillis ?: -1)
        editor.putLong("medicine_time_2", _medicineTimes[1]?.timeInMillis ?: -1)
        editor.putLong("medicine_time_3", _medicineTimes[2]?.timeInMillis ?: -1)
        editor.apply()
    }

    fun loadMedicineTimes(context: Context) {
        val sharedPreferences = context.getSharedPreferences("medicine_prefs", Context.MODE_PRIVATE)
        _medicineTimes[0] = Calendar.getInstance().apply { timeInMillis = sharedPreferences.getLong("medicine_time_1", -1) }
        _medicineTimes[1] = Calendar.getInstance().apply { timeInMillis = sharedPreferences.getLong("medicine_time_2", -1) }
        _medicineTimes[2] = Calendar.getInstance().apply { timeInMillis = sharedPreferences.getLong("medicine_time_3", -1) }
    }
}
