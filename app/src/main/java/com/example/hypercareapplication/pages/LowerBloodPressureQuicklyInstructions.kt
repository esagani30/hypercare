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
fun LowerBloodPressureQuicklyInstructions(navController: NavController) {
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
            painter = painterResource(id = R.drawable.lowblood), // Add appropriate image
            contentDescription = "Lower Blood Pressure Image",
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
                    text = "How to Lower Blood Pressure Quickly",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = TextStyle(fontFamily = customRobotoFontFamily)
                )

                Text(
                    text = "If you need to lower your blood pressure quickly, it's important to stay calm and take steps to reduce your stress and relax your body. Here are some strategies that may help:",
                    fontSize = 16.sp,
                    color = Color.Black,
                    style = TextStyle(fontFamily = customRobotoFontFamily)
                )

                Spacer(modifier = Modifier.height(16.dp))

                val quickLoweringTips = listOf(
                    "1. Practice Deep Breathing\n" +
                            "- Deep breathing exercises can help reduce stress and lower blood pressure. Try this technique:\n" +
                            "  - Sit comfortably and take a deep breath in through your nose for 4 seconds.\n" +
                            "  - Hold your breath for 4 seconds.\n" +
                            "  - Exhale slowly through your mouth for 4-6 seconds.\n" +
                            "  - Repeat for several minutes.",

                    "2. Relax and De-stress\n" +
                            "- If you're feeling stressed or anxious, try relaxation techniques like meditation, yoga, or simply sitting quietly in a calm environment. Stress hormones can elevate blood pressure, so relaxing can help reverse this effect.",

                    "3. Take a Warm Bath or Shower\n" +
                            "- Soaking in a warm bath or having a warm shower can help relax your body and lower your blood pressure. Avoid hot water that could potentially cause discomfort or dizziness.",

                    "4. Drink Water\n" +
                            "- Hydration is important for maintaining healthy blood pressure. Drink a glass of water, as dehydration can sometimes cause a spike in blood pressure.",

                    "5. Eat Foods High in Potassium\n" +
                            "- Potassium helps balance the effects of sodium and can help lower blood pressure. Eating foods like bananas, sweet potatoes, spinach, or beans may help lower your blood pressure.",

                    "6. Reduce Salt Intake\n" +
                            "- Excessive sodium can cause a rise in blood pressure. If you have a high-salt meal, drinking plenty of water and reducing salt intake afterward can help.",

                    "7. Avoid Caffeine and Alcohol\n" +
                            "- Caffeine and alcohol can raise blood pressure in some people. Avoid them, especially when you're feeling that your blood pressure is elevated.",

                    "8. Engage in Light Physical Activity\n" +
                            "- Physical activity like walking or light stretching can improve circulation and reduce blood pressure in some cases. However, avoid intense physical exercise if you have a very high blood pressure reading.",

                    "9. Take Magnesium Supplements\n" +
                            "- Magnesium can help relax blood vessels. If you're low in magnesium, it may contribute to higher blood pressure. Magnesium supplements or magnesium-rich foods like nuts, seeds, and leafy greens can support blood pressure regulation.",

                    "10. Sit or Lie Down and Elevate Your Feet\n" +
                            "- Elevating your feet can help improve blood circulation and reduce blood pressure. Sit or lie down and raise your feet above the level of your heart for 20 minutes.",

                    "11. Avoid Stressful Situations\n" +
                            "- If possible, avoid situations that may further elevate your stress or anxiety levels. Taking a break in a quiet environment can help calm your nerves.",

                    "When to Seek Medical Help:\n" +
                            "- If your blood pressure reading is extremely high (e.g., 180/120 mmHg or higher), you should seek medical attention immediately, especially if you experience symptoms like chest pain, shortness of breath, or severe headache. High blood pressure that isn't controlled could lead to more serious health complications.\n" +
                            "- For ongoing high blood pressure management, consult your healthcare provider about long-term treatment options."
                )

                quickLoweringTips.forEach { tip ->
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
                    text = "By following these tips, you can quickly help lower your blood pressure. However, if you experience any severe symptoms, consult a healthcare provider immediately.",
                    fontSize = 14.sp,
                    color = Color.Black,
                    style = TextStyle(fontFamily = customRobotoFontFamily)
                )
            }
        }
    }
}
