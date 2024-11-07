package com.example.hypercareapplication.pages

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.Calendar

class MealScheduleViewModel : ViewModel() {
    // Change this from val to var to allow reassignment
    private var _mealTimes: MutableList<Calendar?> = MutableList(3) { null }
    val mealTimesList: List<Calendar?> get() = _mealTimes

    fun setMealTime(index: Int, time: Calendar) {
        if (index in _mealTimes.indices) {
            _mealTimes[index] = time
        }
    }

    fun clearMealTimes(context: Context) {
        _mealTimes.fill(null) // Clear the meal times
        saveMealTimes(context) // Save the changes
        cancelMealAlarms(context) // Cancel any existing alarms
    }

    private fun cancelMealAlarms(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        if (alarmManager != null) {
            // Cancel alarms for breakfast, lunch, and dinner
            val mealNames = listOf("Breakfast Time", "Lunch Time", "Dinner Time")
            for (mealName in mealNames) {
                val intent = Intent(context, MealAlarmReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    mealName.hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                alarmManager.cancel(pendingIntent)
            }
        }
    }

    fun saveMealTimes(context: Context) {
        val sharedPreferences = context.getSharedPreferences("meal_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("meal_time_1", _mealTimes[0]?.timeInMillis ?: -1)
        editor.putLong("meal_time_2", _mealTimes[1]?.timeInMillis ?: -1)
        editor.putLong("meal_time_3", _mealTimes[2]?.timeInMillis ?: -1)
        editor.apply()
    }

    fun loadMealTimes(context: Context) {
        val sharedPreferences = context.getSharedPreferences("meal_prefs", Context.MODE_PRIVATE)
        _mealTimes[0] = Calendar.getInstance().apply { timeInMillis = sharedPreferences.getLong("meal_time_1", -1) }
        _mealTimes[1] = Calendar.getInstance().apply { timeInMillis = sharedPreferences.getLong("meal_time_2", -1) }
        _mealTimes[2] = Calendar.getInstance().apply { timeInMillis = sharedPreferences.getLong("meal_time_3", -1) }
    }
}


