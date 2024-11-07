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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hypercareapplication.R
import androidx.compose.ui.text.font.Font


val robotoFontFamily = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_bold, FontWeight.Bold)
)

@Composable
fun LoginOrSignup(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.loginorsignup),
            contentDescription = "ID Image",
            modifier = Modifier
                .size(250.dp)
                .padding(0.dp)
        )

        Text(
            text = "Login or Signup",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontFamily = robotoFontFamily
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                navController.navigate("signup")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = Modifier
                .width(150.dp)
                .height(40.dp)
        ) {
            Text(
                text = "Sign Up",
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = robotoFontFamily
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "or",
            color = Color.Black,
            fontFamily = robotoFontFamily
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                navController.navigate("login")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = Modifier
                .width(150.dp)
                .height(40.dp)
        ) {
            Text(
                text = "Login",
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = robotoFontFamily
            )
        }
    }
}
