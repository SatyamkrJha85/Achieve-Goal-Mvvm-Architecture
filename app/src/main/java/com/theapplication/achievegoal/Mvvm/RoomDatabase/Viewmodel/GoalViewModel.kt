package com.theapplication.achievegoal.Mvvm.RoomDatabase.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theapplication.achievegoal.Mvvm.RoomDatabase.Application.GoalApplication
import com.theapplication.achievegoal.Mvvm.RoomDatabase.Entity.GoalEntity
import com.theapplication.achievegoal.Utils.scheduleNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoalViewModel(application: Application):AndroidViewModel(application) {
    val goalDao = GoalApplication.goalDatabase.getGoalDao()
    val goalList:LiveData<List<GoalEntity>> = goalDao.getallgoal()

    val totalCompleteGoals: LiveData<Int> = goalDao.getTotalDoneGoals()
    val totalOngoingGoals: LiveData<Int> = goalDao.getTotalOngoingGoals()
    val totalDeletedGoals: LiveData<Int> = goalDao.getTotalDeletedGoals()

    // LiveData for total deleted goals
//    fun getTotalDeletedGoals(): LiveData<Int> {
//        return goalDao.getTotalDeletedGoals()
//    }

    fun addgoal(goalEntity: GoalEntity){
        viewModelScope.launch(Dispatchers.IO) {
            goalDao.addgoal(goalEntity)
            scheduleNotification(getApplication(), goalEntity)

        }
    }

    // Add a function to get the total number of tasks
    fun getTotalgoals(): LiveData<Int> {
        return goalDao.getTotalgoal()
    }


    fun deletegoal(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            goalDao.deletegoal(id)
        }
    }




    fun updateGoalStatus(goalEntity: GoalEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!goalEntity.isCompleted) {
                goalDao.updateGoalStatus(goalEntity.id, true)
            } else {
                goalDao.deletegoal(goalEntity.id)
            }
        }
    }

    // Function to get the total number of goals
//    fun getTotalGoals(): LiveData<Int> {
//        return goalDao.getTotalgoal()
//    }

    // Function to get the total number of done goals
//    fun getTotalDoneGoals(): LiveData<Int> {
//        return goalDao.getTotalDoneGoals()
//    }

    // Function to get the total number of ongoing goals
//    fun getTotalOngoingGoals(): LiveData<Int> {
//        return goalDao.getTotalOngoingGoals()
//    }





}