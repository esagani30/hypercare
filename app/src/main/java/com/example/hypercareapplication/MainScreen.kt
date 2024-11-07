package com.example.hypercareapplication

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hypercareapplication.pages.History
import com.example.hypercareapplication.pages.HomePage
import com.example.hypercareapplication.pages.MealScheduleScreen
import com.example.hypercareapplication.pages.MedicineSchedule

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val bloodPressureViewModel: BloodPressureViewModel = viewModel()

    val navItemList = listOf(
        NavItem(icon = painterResource(id = R.drawable.home)),
        NavItem(icon = painterResource(id = R.drawable.bp)),
        NavItem(icon = painterResource(id = R.drawable.meal)),
        NavItem(icon = painterResource(id = R.drawable.medicine)),
        NavItem(icon = painterResource(id = R.drawable.history)),
    )

    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .height(80.dp),
                containerColor = Color(0xFFE05E64), // New background color
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = {
                            Icon(
                                painter = navItem.icon,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(
            modifier = Modifier.padding(innerPadding),
            selectedIndex = selectedIndex,
            navController = navController,
            authViewModel = authViewModel,
            viewModel = bloodPressureViewModel
        )
    }
}

@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    navController: NavController,
    authViewModel: AuthViewModel,
    viewModel: BloodPressureViewModel
) {
    when (selectedIndex) {
        0 -> HomePage(navController, viewModel)
        1 -> LogBp(viewModel)
        2 -> MealScheduleScreen()
        3 -> MedicineSchedule()
        4 -> History(viewModel, authViewModel, navController)
    }
}
