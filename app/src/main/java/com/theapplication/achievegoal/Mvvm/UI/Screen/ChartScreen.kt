package com.theapplication.achievegoal.Mvvm.UI.Screen

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theapplication.achievegoal.Mvvm.RoomDatabase.Viewmodel.GoalViewModel
import com.theapplication.achievegoal.ui.theme.backgroundColor
import kotlinx.coroutines.launch

@Composable
fun ChartScreen(goalViewModel: GoalViewModel) {
    val totalDeletedGoals by goalViewModel.totalDeletedGoals.observeAsState(initial = 0)
    val totalDoneGoals by goalViewModel.totalCompleteGoals.observeAsState(initial = 0)
    val totalOngoingGoals by goalViewModel.totalOngoingGoals.observeAsState(initial = 0)

    val chartData = listOf(
        PieChartDataPoint(value = totalDeletedGoals.toFloat(), title = "Deleted Goal", color = Color.Red),
        PieChartDataPoint(value = totalDoneGoals.toFloat(), title = "Complete Goal", color = Color.Green),
        PieChartDataPoint(value = totalOngoingGoals.toFloat(), title = "Ongoing Goal", color = Color.Blue)
    )

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PieChart(
                data = chartData,
                modifier = Modifier.size(250.dp)
            )

            Box(modifier = Modifier.padding(top = 50.dp)
                , contentAlignment = Alignment.Center){

            Row(
                modifier = Modifier
                    .width(300.dp)
                    .height(100.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(backgroundColor),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center

                ) {

                    Column {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Box(modifier = Modifier
                                .size(20.dp)
                                .clip(shape = CircleShape)
                                .background(
                                    Color.Green
                                )){}

                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "Completed Goals $totalDoneGoals", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)

                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(modifier = Modifier
                                .size(20.dp)
                                .clip(shape = CircleShape)
                                .background(
                                    Color.Blue
                                )){}
                            Spacer(modifier = Modifier.width(10.dp))

                            Text(text = "Ongoing Goals $totalOngoingGoals",fontSize = 22.sp, fontWeight = FontWeight.SemiBold)

                        }

                    }


            }
            }


        }
    }
}

@Composable
fun PieChart(
    data: List<PieChartDataPoint>,
    modifier: Modifier = Modifier,
    innerRadiusFraction: Float = 0.5f // Adjust the inner radius fraction to create a donut chart effect
) {
    val totalValue = data.sumByDouble { it.value.toDouble() }.toFloat().takeIf { it > 0 } ?: 1f

    val animatedAngles = remember(data) { List(data.size) { Animatable(0f) } }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(data) {
        data.forEachIndexed { index, slice ->
            coroutineScope.launch {
                animatedAngles[index].animateTo(
                    targetValue = (slice.value / totalValue) * 360f,
                    animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                )
            }
        }
    }

    Canvas(
        modifier = modifier
    ) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val outerRadius = size.width.coerceAtMost(size.height) / 2f
        val innerRadius = outerRadius * innerRadiusFraction

        var startAngle = -90f

        data.forEachIndexed { index, slice ->
            val sweepAngle = animatedAngles[index].value
            drawArc(
                color = slice.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(centerX - outerRadius, centerY - outerRadius),
                size = Size(outerRadius * 2, outerRadius * 2),
            )

            val angle = startAngle + sweepAngle / 2
            val labelRadius = outerRadius + 16.dp.toPx()

            val labelX = centerX + labelRadius * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
            val labelY = centerY + labelRadius * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "${slice.title} (${(slice.value / totalValue * 100).toInt()}%)",
                    labelX,
                    labelY,
                    android.graphics.Paint().apply {
                        textSize = 10.sp.toPx()
                        textAlign = android.graphics.Paint.Align.CENTER
                        color = android.graphics.Color.BLACK
                    }
                )
            }

            startAngle += sweepAngle
        }

        drawInnerCircle(centerX, centerY, innerRadius)
    }
}

private fun DrawScope.drawInnerCircle(centerX: Float, centerY: Float, radius: Float) {
    drawCircle(
        color = Color.White,
        radius = radius,
        center = Offset(centerX, centerY)
    )
}

data class PieChartDataPoint(
    val value: Float,
    val title: String,
    val color: Color
)
