package com.theapplication.achievegoal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.theapplication.achievegoal.Mvvm.RoomDatabase.Viewmodel.GoalViewModel
import com.theapplication.achievegoal.Mvvm.Utils.NavGraph
import com.theapplication.achievegoal.ui.theme.AchieveGoalTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val goalviewmodel = ViewModelProvider(this).get(GoalViewModel::class.java)

        setContent {
            AchieveGoalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavGraph(goalviewmodel)
                }
            }
        }
    }
}

