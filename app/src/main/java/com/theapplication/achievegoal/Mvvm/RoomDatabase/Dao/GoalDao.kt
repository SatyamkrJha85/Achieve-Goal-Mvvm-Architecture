package com.theapplication.achievegoal.Mvvm.RoomDatabase.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.theapplication.achievegoal.Mvvm.RoomDatabase.Entity.GoalEntity


@Dao
interface GoalDao {
    @Query("SELECT * FROM goalentity")
    fun getallgoal():LiveData<List<GoalEntity>>

    @Query("SELECT COUNT(*) FROM goalentity")
    fun getTotalgoal(): LiveData<Int>

    @Insert
    suspend fun addgoal(goalEntity: GoalEntity)

    @Query("DELETE FROM GoalEntity WHERE ID=:id")
    suspend fun deletegoal(id:Int)

    @Query("UPDATE goalentity SET isCompleted = :isDone WHERE id = :id")
    suspend fun updateGoalStatus(id: Int, isDone: Boolean)

    @Query("SELECT COUNT(*) FROM goalentity WHERE isCompleted = 1")
    fun getTotalDoneGoals(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM goalentity WHERE isCompleted = 0")
    fun getTotalOngoingGoals(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM GoalEntity WHERE status = 'DELETED'")
    fun getTotalDeletedGoals(): LiveData<Int>
}