package com.example.hypercareapplication.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import customRobotoFontFamily
import com.example.hypercareapplication.R

@Composable
fun BloodPressureInstructions(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Spacer before the back button for visibility
        Spacer(modifier = Modifier.height(20.dp))

        // Aligning the back button to the far left
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 0.dp)
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.CenterVertically) // Center vertically within the row
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back_button), // Your back icon here
                    contentDescription = "Back Button",
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Spacing before the title

        Image(
            painter = painterResource(id = R.drawable.measure), // Replace with appropriate image
            contentDescription = "Blood Pressure Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "How to Measure Your Blood Pressure at Home",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = TextStyle(fontFamily = customRobotoFontFamily)
                )

                Spacer(modifier = Modifier.height(16.dp))

                val measurementTips = listOf(
                    "1. Find a quiet, comfortable place to sit.",
                    "2. Place your arm on a table with your palm up.",
                    "3. Wrap the cuff around your upper arm.",
                    "4. Start the blood pressure monitor.",
                    "5. Remain still and do not talk during measurement.",
                    "6. Record your blood pressure results.",
                    "7. Repeat twice and average results if needed."
                )

                measurementTips.forEach { tip ->
                    Text(
                        text = tip,
                        fontSize = 20.sp,
                        color = Color.Black,
                        style = TextStyle(fontFamily = customRobotoFontFamily)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "By following these instructions, you can accurately measure your blood pressure at home.",
                    fontSize = 14.sp,
                    color = Color.Black,
                    style = TextStyle(fontFamily = customRobotoFontFamily)
                )
            }
        }
    }
}
