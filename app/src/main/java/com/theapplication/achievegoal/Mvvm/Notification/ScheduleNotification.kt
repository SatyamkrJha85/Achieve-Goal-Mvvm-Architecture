package com.theapplication.achievegoal.Utils

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.theapplication.achievegoal.Mvvm.RoomDatabase.Entity.GoalEntity
import com.theapplication.achievegoal.Mvvm.Notification.TaskNotificationReceiver
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat", "ScheduleExactAlarm")
fun scheduleNotification(context: Context, goal: GoalEntity) {
    val notificationId = goal.id
    val title = goal.title
    val description = goal.description
    val iconResId = goal.iconResId

    val intent = Intent(context, TaskNotificationReceiver::class.java).apply {
        putExtra("notificationId", notificationId)
        putExtra("title", "Your Goal $title is Waiting for you 🤖")
        putExtra("description", "$description 🔥 Please complete this Goal")
        putExtra("iconResId", iconResId)
    }


    val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
        context,
        notificationId,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // Parse the time string from goalEntity into Calendar
    val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    val date = dateFormat.parse(goal.time)
    val calendar = Calendar.getInstance().apply {
        time = date ?: Date() // Use current time if parsing fails
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    // Check if the scheduled time is in the past, adjust if necessary
    if (calendar.before(Calendar.getInstance())) {
        calendar.add(Calendar.DAY_OF_YEAR, 1) // Schedule for the next day
    }

    // Schedule alarm based on API level
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
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
}
