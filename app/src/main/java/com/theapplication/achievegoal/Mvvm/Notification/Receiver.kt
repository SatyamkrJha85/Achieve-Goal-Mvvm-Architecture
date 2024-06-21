package com.theapplication.achievegoal.Mvvm.Notification

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.theapplication.achievegoal.MainActivity
import com.theapplication.achievegoal.R

class TaskNotificationReceiver : BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra("notificationId", 0)
        val title = intent.getStringExtra("title") ?: "Task Reminder"
        val description = intent.getStringExtra("description") ?: "You have a task to complete."
        val iconResId = intent.getIntExtra("iconResId", R.drawable.heart)



        // Intent to open MainActivity when notification is tapped
        val resultIntent = Intent(context, MainActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, "task_channel")
            .setSmallIcon(iconResId)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)


        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }
}
