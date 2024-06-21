package com.theapplication.achievegoal.Mvvm.UI.Screen

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theapplication.achievegoal.Mvvm.RoomDatabase.Entity.GoalEntity
import com.theapplication.achievegoal.Mvvm.RoomDatabase.Entity.GoalStatus
import com.theapplication.achievegoal.Mvvm.RoomDatabase.Viewmodel.GoalViewModel
import com.theapplication.achievegoal.R
import com.theapplication.achievegoal.ui.theme.backgroundColor

@Composable
fun HomeScreen(goalViewModel: GoalViewModel) {


    val totalgoalLiveData = goalViewModel.getTotalgoals()
    val totalTasks by totalgoalLiveData.observeAsState(0)


    val goallist by goalViewModel.goalList.observeAsState()

    LazyColumn {
        item {
            Box {

                Column {


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(12.dp))
                                .size(40.dp)
                                .background(backgroundColor), contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.gridcategory),
                                contentDescription = null
                            )
                        }


                        Text(text = "DON'T GIVE UP", color = Color.Blue, fontSize = 20.sp)

                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(4.dp))
                                .size(40.dp)
                                .background(backgroundColor), contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.exercise),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }


                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, start = 20.dp)
                    ) {
                        Text(
                            text = "2 Big Challenges",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        Text(
                            text = "Ambitious Person aren't you?",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, start = 20.dp)
                    ) {
                        homepagecard(
                            title = "Life Style",
                            discription = "Become a morning person",
                            icon = R.drawable.alarm
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        homepagecard(
                            title = "Healthy Life",
                            discription = "your health is most important",
                            icon = R.drawable.heart
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, start = 20.dp)
                    ) {
                        Text(
                            text = "Today's Planning",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        Text(
                            text = "You have $totalTasks action to do",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    }
                }
            }
        }


        goallist?.let {
            if (it.isEmpty()) {

                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Empty Goal", color = Color.Black, fontSize = 20.sp)
                    }
                }
            } else {
                itemsIndexed(it) { index: Int, item: GoalEntity ->

                    ItemList(goalEntity = item) {
                        goalViewModel.updateGoalStatus(item)
                    }
                }
            }

        }

    }
}


@Composable
fun homepagecard(title: String, discription: String, icon: Int) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .width(150.dp)
            .height(200.dp)
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(start = 20.dp, top = 10.dp)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = discription,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Reminder",
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
            Text(
                text = "Every Day",
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

        }
    }
}

@Composable
fun ItemList(goalEntity: GoalEntity, onDelete: () -> Unit) {
    val local = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
    ) {

        ElevatedCard(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(320.dp)
                .height(120.dp),
            colors = CardDefaults.cardColors(backgroundColor)

        ) {
            Column {
                Row(
                    modifier = Modifier.padding(start = 20.dp, top = 5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(12.dp))
                            .size(50.dp)
                            .background(Color.White)
                            .clip(
                                shape = RoundedCornerShape(22.dp)
                            ), contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = goalEntity.iconResId),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.padding(start = 20.dp, top = 8.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column {
                                Text(
                                    text = goalEntity.title,
                                    color = Color.Black,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = goalEntity.description,
                                    color = Color.Gray,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.width(150.dp)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .clip(shape = RoundedCornerShape(12.dp))
                                    .size(40.dp)
                                    .clickable {
                                        onDelete()

                                        Toast
                                            .makeText(
                                                local,
                                                "${goalEntity.title} Add to Done \n Double tap for Delete ",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()

                                    }
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Divider(
                    color = if (goalEntity.priority == "Low") {
                        Color.Red
                    } else if (goalEntity.priority == "Medium") {
                        Color.Blue
                    } else {
                        Color.Green
                    },
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.padding(start = 20.dp, top = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.timeremain),
                            contentDescription = null,
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = goalEntity.time, color = Color.Gray)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = goalEntity.reminder, color = Color.Gray)
                    }

                    Row {
                        Box(
                            modifier = Modifier
                                .padding(end = 5.dp, top = 5.dp)
                                .clip(shape = RoundedCornerShape(12.dp))
                                .size(40.dp)
                                .clickable {
                                    onDelete()
                                    Toast
                                        .makeText(
                                            local,
                                            "${goalEntity.title} Deleted ",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                                .background(Color.White), contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null
                            )
                        }
                    }
                }

            }

        }
    }
}