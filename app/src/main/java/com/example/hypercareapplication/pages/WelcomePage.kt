package com.example.hypercare.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hypercareapplication.R


val welcomeRobotoFontFamily = FontFamily(
    Font(R.font.roboto_regular),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_italic, FontWeight.Bold)
)

@Composable
fun WelcomePage(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.doc),
            contentDescription = "Doctor",
            modifier = Modifier
                .size(200.dp)
                .padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Welcome to HyperCare",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp),
            fontFamily = welcomeRobotoFontFamily
        )
        Text(
            text = "Your health companion for managing hypertension.",
            modifier = Modifier.padding(bottom = 24.dp),
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontFamily = welcomeRobotoFontFamily
        )

        Spacer(modifier = Modifier.height(200.dp))
        Button(
            onClick = {
                navController.navigate("login_or_signup")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = Modifier.width(200.dp)
        ) {
            Text(text = "Get Started", fontSize = 20.sp, color = Color.White, fontFamily = welcomeRobotoFontFamily)
        }
    }
}
