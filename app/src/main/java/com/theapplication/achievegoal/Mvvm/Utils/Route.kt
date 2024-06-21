package com.theapplication.achievegoal.Mvvm.Utils

sealed class Route  (val route:String){

    object HomeScreen :Route("homescreen")
    object AddGoalScreen :Route("addgoalscreen")
    object ChartScreen :Route("chartscreen")
    object BottomBarScreen :Route("bottombar")

}