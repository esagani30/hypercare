package com.example.hypercareapplication

import LoginOrSignup
import LoginPage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hypercare.pages.SignupPage
import com.example.hypercare.pages.WelcomePage
import com.example.hypercareapplication.pages.BloodPressureInstructions
import com.example.hypercareapplication.pages.LowerBloodPressureQuicklyInstructions
import com.example.hypercareapplication.pages.PreventBloodPressureInstructions

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                navController.navigate("main") {
                    popUpTo("welcome") { inclusive = true }
                }
            }
            else -> Unit
        }
    }

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable(route = "welcome") {
            WelcomePage(modifier, navController)
        }
        composable(route = "login_or_signup") {
            LoginOrSignup(modifier, navController)
        }
        composable(route = "login") {
            LoginPage(modifier, navController, authViewModel)
        }
        composable(route = "signup") {
            SignupPage(modifier, navController, authViewModel)
        }
        composable(route = "main") {
            MainScreen(modifier, navController, authViewModel)
        }
        composable("prevent_high_blood_pressure") {
            PreventBloodPressureInstructions(navController)
        }
        composable("lower_blood_pressure_quickly_instructions") {
            LowerBloodPressureQuicklyInstructions(navController)
        }
        composable("blood_pressure_instructions") {
            BloodPressureInstructions(navController)
        }
    }
}
