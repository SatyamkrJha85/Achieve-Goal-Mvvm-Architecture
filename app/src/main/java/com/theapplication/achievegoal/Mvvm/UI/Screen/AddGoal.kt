package com.theapplication.achievegoal.Mvvm.UI.Screen

import android.app.TimePickerDialog
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.theapplication.achievegoal.Mvvm.RoomDatabase.Entity.GoalEntity
import com.theapplication.achievegoal.Mvvm.RoomDatabase.Viewmodel.GoalViewModel
import com.theapplication.achievegoal.Mvvm.UI.DataClasses.IconWithName
import com.theapplication.achievegoal.Mvvm.UI.DataClasses.iconsWithNames
import com.theapplication.achievegoal.Mvvm.Utils.Route
import com.theapplication.achievegoal.R
import com.theapplication.achievegoal.ui.theme.backgroundColor
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGoal(goalViewModel: GoalViewModel,navController: NavController) {

    Box() {

        var title = remember {
            mutableStateOf("")
        }
        var discription = remember {
            mutableStateOf("")
        }

        var reminder = remember {
            mutableStateOf("")
        }

        var priority = remember {
            mutableStateOf("")
        }

        var time = remember {
            mutableStateOf("")
        }

        val selectedIcon = remember { mutableStateOf(R.drawable.add) }

        val local = LocalContext.current



        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "New Goal",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .clickable {
                            navController.navigate(Route.HomeScreen.route){
                                popUpTo(0)
                            }
                        }
                        .background(backgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }

            }

            Column {
                edittextfield(
                    title = title,
                    lable = "Goal Title",
                    placeholder = "Water consumption"
                )
                Spacer(modifier = Modifier.height(10.dp))
                edittextfield(
                    title = discription,
                    lable = "Goal Discription",
                    placeholder = "Drink 5 cup water"
                )
                Spacer(modifier = Modifier.height(10.dp))
                ExpenseDropDown(
                    listofItem = listOf("Select Day","Every Day", "Weekly", "Monthly"),
                    "Reminder",
                    onItemSelected = { reminder.value = it })

                Spacer(modifier = Modifier.height(10.dp))

                ExpenseDropDown(
                    listofItem = listOf("Select Priority","Low", "Medium", "High"),
                    "Priority",
                    onItemSelected = { priority.value = it })

                Spacer(modifier = Modifier.height(10.dp))

                timepicker(title = time, lable = "Choose Time")

                Spacer(modifier = Modifier.height(10.dp))

                IconDropDown(
                    iconsWithNames = iconsWithNames,
                    label = "Choose Another Icon"
                ) { it ->
                    selectedIcon.value = it
                }

                Spacer(modifier = Modifier.height(10.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    ElevatedButton(
                        onClick = {
                            if (title.value.isNotEmpty() && discription.value.isNotEmpty() && reminder.value.isNotEmpty() && time.value.isNotEmpty() &&
                                priority.value.isNotEmpty()
                            ) {
                                val list = GoalEntity(
                                    title = title.value,
                                    description = discription.value,
                                    reminder = reminder.value,
                                    priority = priority.value,
                                    time = time.value,
                                    iconResId = selectedIcon.value
                                )

                                goalViewModel.addgoal(list)
                                Toast.makeText(local,"Goal Added",Toast.LENGTH_SHORT).show()
                                title.value=""
                                discription.value=""
                                reminder.value=""
                                priority.value=""
                                time.value=""


                            } else {
                             Toast.makeText(local,"Fill all those Field",Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .width(250.dp)
                            .height(60.dp)
                            .padding(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor
                        ),
                        shape = RoundedCornerShape(
                            topStart = 4.dp,
                            topEnd = 4.dp,
                            bottomStart = 22.dp,
                            bottomEnd = 22.dp
                        )
                    ) {
                        Text(text = "Add Goal", color = Color.Black)
                    }
                }

                Row {
                    showpreviewgoal()
                }


            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun edittextfield(title: MutableState<String>, lable: String, placeholder: String) {
    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(22.dp))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = title.value, onValueChange = { title.value = it },
            label = { Text(text = lable) },
            placeholder = { Text(text = placeholder) },
            colors = TextFieldDefaults.textFieldColors(

                containerColor = backgroundColor,
                errorContainerColor = backgroundColor,
                errorCursorColor = Color.Black,
                cursorColor = Color.Black,
                errorIndicatorColor = backgroundColor,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                disabledTextColor = Color.Black,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                focusedPlaceholderColor = Color.Black,
                errorPlaceholderColor = Color.Black,
                unfocusedPlaceholderColor = Color.Black,
                disabledPlaceholderColor = Color.Black,
                unfocusedLabelColor = Color.Gray,
                disabledLabelColor = Color.Gray,
                errorLabelColor = Color.Gray,
                focusedLabelColor = Color.Gray

            ),
            modifier = Modifier
                .width(330.dp)
                .height(60.dp)
                .clip(shape = RoundedCornerShape(12.dp))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun timepicker(title: MutableState<String>, lable: String) {


    val openDialog = remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    val selectedHour = timePickerState.hour
                    val selectedMinute = timePickerState.minute
                    val isAM = selectedHour < 12
                    val hour = if (selectedHour % 12 == 0) 12 else selectedHour % 12
                    val period = if (isAM) "AM" else "PM"
                    title.value = String.format("%02d:%02d %s", hour, selectedMinute, period)
                    openDialog.value = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text("Cancel")
                }
            },
            text = {
                TimePicker(
                    state = timePickerState,
                    layoutType = TimePickerLayoutType.Vertical
                )
            }
        )
    }


    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(22.dp))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            value = title.value, onValueChange = { title.value = it },
            readOnly = true,
            label = { Text(text = lable) },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.time),
                    contentDescription = null,
                    tint = Color.Black
                )
            },

            colors = TextFieldDefaults.textFieldColors(

                containerColor = backgroundColor,
                errorContainerColor = backgroundColor,
                errorCursorColor = backgroundColor,
                errorIndicatorColor = backgroundColor,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                disabledTextColor = Color.Black,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                focusedPlaceholderColor = Color.Black,
                errorPlaceholderColor = Color.Black,
                unfocusedPlaceholderColor = Color.Black,
                disabledPlaceholderColor = Color.Black,
                unfocusedLabelColor = Color.Gray,
                disabledLabelColor = Color.Gray,
                errorLabelColor = Color.Gray,
                focusedLabelColor = Color.Gray

            ),
            modifier = Modifier
                .width(330.dp)
                .clickable(onClick = {

                    openDialog.value = true

                })

                .height(60.dp)
                .clip(shape = RoundedCornerShape(12.dp)),
            enabled = false
        )

    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDropDown(
    listofItem: List<String>,
    label: String,
    onItemSelected: (item: String) -> Unit
) {
    val expended = remember {
        mutableStateOf(false)
    }

    val selecteditem = remember {
        mutableStateOf<String>(listofItem[0])
    }
    ExposedDropdownMenuBox(expanded = expended.value, onExpandedChange = { expended.value = it }) {

        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(22.dp))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = selecteditem.value, onValueChange = { },
                label = { Text(text = label) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.Black
                    )
                },

                colors = TextFieldDefaults.textFieldColors(

                    containerColor = backgroundColor,
                    errorContainerColor = backgroundColor,
                    errorCursorColor = backgroundColor,
                    errorIndicatorColor = backgroundColor,
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    focusedPlaceholderColor = Color.Black,
                    errorPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Black,
                    disabledPlaceholderColor = Color.Black,
                    unfocusedLabelColor = Color.Gray,
                    disabledLabelColor = Color.Gray,
                    errorLabelColor = Color.Gray,
                    focusedLabelColor = Color.Gray

                ),
                modifier = Modifier
                    .width(330.dp)
                    .height(60.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .menuAnchor(),
                enabled = false
            )
        }

        ExposedDropdownMenu(
            expanded = expended.value, onDismissRequest = { }, modifier = Modifier
                .background(
                    backgroundColor
                )
                .padding(start = 10.dp)
        ) {
            listofItem.forEach {
                DropdownMenuItem(text = { Text(text = it, color = Color.Black) },
                    onClick = {
                        selecteditem.value = it
                        onItemSelected(selecteditem.value)
                        expended.value = false
                    })
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconDropDown(
    iconsWithNames: List<IconWithName>,
    label: String,
    onItemSelected: (item: Int) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf(iconsWithNames[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it }
    ) {
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(22.dp))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = "",  // Leave the TextField empty as we're only displaying the icon
                onValueChange = {},
                label = { Text(text = label) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.Black
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = selectedItem.value.iconResId),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(30.dp)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = backgroundColor,
                    errorContainerColor = backgroundColor,
                    errorCursorColor = backgroundColor,
                    errorIndicatorColor = backgroundColor,
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    focusedPlaceholderColor = Color.Black,
                    errorPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Black,
                    disabledPlaceholderColor = Color.Black,
                    unfocusedLabelColor = Color.Gray,
                    disabledLabelColor = Color.Gray,
                    errorLabelColor = Color.Gray,
                    focusedLabelColor = Color.Gray
                ),
                modifier = Modifier
                    .width(330.dp)
                    .height(60.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .menuAnchor()
                    .clickable { expanded.value = true },
                enabled = false
            )
        }

        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .background(backgroundColor)
                .padding(start = 10.dp)
        ) {
            iconsWithNames.forEach { iconWithName ->
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = iconWithName.iconResId),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(30.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = iconWithName.name, color = Color.Black)
                        }
                    },
                    onClick = {
                        selectedItem.value = iconWithName
                        onItemSelected(iconWithName.iconResId)
                        expanded.value = false
                    }
                )
            }
        }
    }
}


@Composable
fun showpreviewgoal() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    ) {
        Text(
            text = "Goal Preview Example",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
        )

        ElevatedCard(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(320.dp)
                .height(100.dp),
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
                        Icon(
                            painter = painterResource(id = R.drawable.carrent),
                            contentDescription = null,
                            tint = Color.Blue,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.padding(start = 20.dp, top = 8.dp)
                    ) {
                        Text(
                            text = "Traveling Journey",
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Daily 5 K/M",
                            color = Color.Gray,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }


                }
                Spacer(modifier = Modifier.height(5.dp))
                Divider(
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )
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
                    Text(text = "Deadline", color = Color.Gray)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "1 Jaunvary 2025", color = Color.Gray)
                }
            }

        }
    }
}