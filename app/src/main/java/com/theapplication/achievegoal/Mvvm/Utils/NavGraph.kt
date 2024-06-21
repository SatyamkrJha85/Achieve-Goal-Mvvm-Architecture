package com.theapplication.achievegoal.Mvvm.Utils

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.theapplication.achievegoal.Mvvm.RoomDatabase.Viewmodel.GoalViewModel
import com.theapplication.achievegoal.Mvvm.UI.Screen.BottomBar

@Composable
fun NavGraph(goalViewModel: GoalViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.BottomBarScreen.route) {

        composable(Route.BottomBarScreen.route){
            BottomBar(goalViewModel)
        }


    }
}
