package com.theapplication.achievegoal.Mvvm.UI.DataClasses

import com.theapplication.achievegoal.R

data class IconWithName(val iconResId: Int, val name: String)

val iconsWithNames = listOf(
    IconWithName(R.drawable.calling, "Calling"),
    IconWithName(R.drawable.caretaker, "Care Taker"),
    IconWithName (R.drawable.cleaning, "Cleaning"),
    IconWithName (R.drawable.exercise, "Exercise"),
    IconWithName(R.drawable.clothwash, "Cloth Wash"),
    IconWithName(R.drawable.drinkwater, "Drink Water"),
    IconWithName(R.drawable.gardening, "Gardening"),
    IconWithName(R.drawable.market, "Market"),
    IconWithName(R.drawable.music, "Music"),
    IconWithName(R.drawable.programmer, "Programming"),
    IconWithName(R.drawable.readingbook, "Read a Book"),
            IconWithName (R.drawable.other, "Others"),

)


