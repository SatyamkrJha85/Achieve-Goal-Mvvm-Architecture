package com.theapplication.achievegoal.Mvvm.RoomDatabase.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class GoalEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val title: String,
    val description: String,
    val reminder: String,
    val priority: String,
    val time: String,
    val iconResId: Int,
    val isCompleted: Boolean = false,
    val status: GoalStatus = GoalStatus.ONGOING
)

enum class GoalStatus {
    ONGOING,
    COMPLETED,
    DELETED
}