package com.stepa075.roulettecompose.rule_screen

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stepa075.roulettecompose.R
import com.stepa075.roulettecompose.ui.theme.Reed
import com.stepa075.roulettecompose.utils.NumberUtil
import com.stepa075.roulettecompose.utils.ColorOfNumber
import kotlin.math.roundToInt

@Composable
fun RuleScreen() {

    var greenBlackRed by remember {
        mutableStateOf(0)
    }

    var rotationValue by remember {
        mutableStateOf(0f)
    }
    var number by remember {
        mutableStateOf(0)
    }
    val angle: Float by animateFloatAsState(targetValue = rotationValue,
        animationSpec = tween(
            durationMillis = 2000,
            easing = LinearOutSlowInEasing
        ),
        finishedListener = {
            val indexOfArray = (360f - (it % 360)) / (360f / NumberUtil.list.size)
            Log.d("MyLOg", "${indexOfArray.roundToInt()}")
            number = NumberUtil.list[indexOfArray.roundToInt()]
            if (indexOfArray.roundToInt() == 0) {
                greenBlackRed = 0
            }
            if ((indexOfArray.roundToInt() % 2) == 0) {
                greenBlackRed = 1
            } else {
                greenBlackRed = 2
            }

        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .wrapContentHeight()
                .wrapContentWidth(),
            text = number.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            color = Color.White
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .wrapContentHeight()
                .wrapContentWidth(),
            text = ColorOfNumber.listOfColor[greenBlackRed],
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,

            color = when (greenBlackRed) {
                0 -> Color.White
                1 -> Color.Black
                else -> Color.Red
            }
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ruleta),
                contentDescription = "ruleta",
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(angle)
            )

            Image(
                painter = painterResource(id = R.drawable.flecha),
                contentDescription = "flecha",
                modifier = Modifier.fillMaxSize()
            )


        }
        Button(
            onClick = {
                rotationValue = (720..1080).random().toFloat() + angle
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Reed),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = "Start",
                color = Color.White
            )
        }

    }
}