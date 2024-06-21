package com.theapplication.achievegoal.Mvvm.RoomDatabase.Application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.room.Room
import com.theapplication.achievegoal.Mvvm.RoomDatabase.Database.GoalDatabase

class GoalApplication:Application() {
    companion object{
        lateinit var goalDatabase: GoalDatabase
    }

    override fun onCreate() {
        super.onCreate()
        goalDatabase=Room.databaseBuilder(
            applicationContext,
            GoalDatabase::class.java,
            GoalDatabase.name
        ).build()

        createNotificationChannel()

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Task Channel"
            val descriptionText = "Channel for task reminders"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("task_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}