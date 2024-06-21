package com.theapplication.achievegoal.Mvvm.UI.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.theapplication.achievegoal.Mvvm.RoomDatabase.Viewmodel.GoalViewModel
import com.theapplication.achievegoal.Mvvm.Utils.Route
import com.theapplication.achievegoal.R
import com.theapplication.achievegoal.ui.theme.IconColor

@Composable
fun BottomBar(goalViewModel: GoalViewModel) {


    val navController = rememberNavController()

    Scaffold(
        containerColor = Color.White,
        bottomBar = { MyBottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable( Route.HomeScreen.route) {
                HomeScreen(goalViewModel)
            }
            composable( Route.AddGoalScreen.route) {
                AddGoal(goalViewModel,navController)
            }
            composable( Route.ChartScreen.route) {
                ChartScreen(goalViewModel)
            }
        }
    }
}

@Composable
fun MyBottomBar(navController: NavHostController) {

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val (selectedRoute, setSelectedRoute) = remember { mutableStateOf(Route.HomeScreen.route) }

    // Define navigation items
    val homeItem = BottomNavItem("Home", Route.HomeScreen.route, Icons.Rounded.Home)
    val addGoalItem = BottomNavItem("Add Goal", Route.AddGoalScreen.route, Icons.Rounded.Add)
    val ChartScreenitem = BottomNavItem("Chart", Route.ChartScreen.route, Icons.Rounded.Check)

    Row(
        modifier = Modifier
            .background(Color.White)
            //.height(56.dp)
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Left side IconButton
        IconButton(
            onClick = {
                if (currentRoute != homeItem.route) {
                    navController.navigate(homeItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                    setSelectedRoute(homeItem.route)
                }
            },

           // modifier = Modifier.padding(start = 16.dp)
        ) {
            Icon(
                imageVector = homeItem.icon,
                contentDescription = homeItem.title,
                tint = if (selectedRoute == homeItem.route) IconColor else Color.Gray,
                modifier = Modifier.size(34.dp)
            )


        }

        // Spacer to center align FAB
      //  Spacer(modifier = Modifier.weight(1f))

        // Floating Action Button in the center
        FloatingActionButton(
            onClick = {
                if (currentRoute != addGoalItem.route) {
                    navController.navigate(addGoalItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                    setSelectedRoute(addGoalItem.route)

                }
            },
            modifier = Modifier

                .align(Alignment.CenterVertically),
           // fabPosition = FabPosition.Center,
            elevation = FloatingActionButtonDefaults.elevation(),
            containerColor =  if (selectedRoute == addGoalItem.route) IconColor else Color.Gray,
            shape = CircleShape
        ){
            Icon(imageVector = addGoalItem.icon, contentDescription = addGoalItem.title,
                tint = if (selectedRoute == addGoalItem.route) Color.White else Color.White,
            )
        }

        // Spacer for right side alignment
      //  Spacer(modifier = Modifier.weight(1f))

        // Right side IconButton
        IconButton(
            onClick = {
                if (currentRoute != ChartScreenitem.route) {
                    navController.navigate(ChartScreenitem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                    setSelectedRoute(ChartScreenitem.route)
                }
            },
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Icon(
               painter = painterResource(id = R.drawable.chart),
                contentDescription = homeItem.title,
                tint = if (selectedRoute == ChartScreenitem.route) IconColor else Color.Gray,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: ImageVector,
    val secondicon:Int=0
)