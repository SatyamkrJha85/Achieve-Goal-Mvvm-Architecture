package com.theapplication.achievegoal.Mvvm.RoomDatabase.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.theapplication.achievegoal.Mvvm.RoomDatabase.Dao.GoalDao
import com.theapplication.achievegoal.Mvvm.RoomDatabase.Entity.GoalEntity


@Database(entities = [GoalEntity::class], version = 1, exportSchema = false)
abstract class GoalDatabase:RoomDatabase() {
    companion object{
        const val name = "Goal"
    }
    abstract fun getGoalDao():GoalDao
}