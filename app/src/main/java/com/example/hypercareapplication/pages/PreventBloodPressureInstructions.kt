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
fun PreventBloodPressureInstructions(navController: NavController) {
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

        Image(
            painter = painterResource(id = R.drawable.prevent),
            contentDescription = "Prevent High Blood Pressure Image",
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
                    text = "How to Prevent High Blood Pressure",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = TextStyle(fontFamily = customRobotoFontFamily)
                )

                Spacer(modifier = Modifier.height(16.dp))

                val preventionTips = listOf(
                    "1. Maintain a Healthy Diet:\n" +
                            "- Reduce salt intake: Too much salt (sodium) can raise blood pressure. Aim for less than 2,300 mg of sodium daily, ideally under 1,500 mg if you're at risk.\n" +
                            "- Eat more fruits and vegetables: Potassium, magnesium, and fiber in fruits, vegetables, and whole grains can help lower blood pressure.\n" +
                            "- Limit alcohol: Excessive alcohol raises blood pressure. Moderation is key (up to one drink daily for women, two for men).\n" +
                            "- Limit caffeine: The effect of caffeine on blood pressure varies; best consumed in moderation, especially if sensitive.\n" +
                            "- Healthy fats: Choose sources like nuts, seeds, avocados, and fish.",

                    "2. Exercise Regularly:\n" +
                            "- Aim for at least 150 minutes of moderate-intensity exercise or 75 minutes of vigorous exercise weekly. Activities like walking, jogging, cycling, swimming, or strength training can lower blood pressure.",

                    "3. Maintain a Healthy Weight:\n" +
                            "- Being overweight increases the risk of high blood pressure. Losing even 5-10% of body weight can significantly help.",

                    "4. Avoid Tobacco and Smoking:\n" +
                            "- Smoking and secondhand smoke raise blood pressure. Quitting improves heart health and lowers high blood pressure risk.",

                    "5. Manage Stress:\n" +
                            "- Chronic stress can raise blood pressure. Practice relaxation techniques like deep breathing, meditation, or spending time in nature.",

                    "6. Get Enough Sleep:\n" +
                            "- Insufficient sleep (less than 7 hours per night) can raise blood pressure. Aim for quality sleep each night to support heart health.",

                    "7. Monitor Your Blood Pressure:\n" +
                            "- Regularly check blood pressure, especially if at risk. Early detection aids in managing and preventing hypertension.",

                    "8. Stay Hydrated:\n" +
                            "- Dehydration can increase blood pressure. Ensure you're drinking enough water throughout the day."
                )

                preventionTips.forEach { tip ->
                    Text(
                        text = tip,
                        fontSize = 20.sp,
                        color = Color.Black,
                        style = TextStyle(fontFamily = customRobotoFontFamily)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "By incorporating these lifestyle changes, you can reduce the risk of developing high blood pressure and improve overall health. If you already have high blood pressure, these strategies can help manage it. Always consult a healthcare provider for personalized advice and treatment.",
                    fontSize = 14.sp,
                    color = Color.Black,
                    style = TextStyle(fontFamily = customRobotoFontFamily)
                )
            }
        }
    }
}
