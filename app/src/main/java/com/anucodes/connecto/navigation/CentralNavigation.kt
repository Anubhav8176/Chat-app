package com.anucodes.connecto.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.anucodes.connecto.core.authentication.viewmodel.AuthViewModel
import com.anucodes.connecto.presentation.screens.authentication.LogInScreen
import com.anucodes.connecto.presentation.screens.authentication.SignUpScreen
import com.anucodes.connecto.presentation.screens.home.HomeScreen


@Composable
fun CentralNavigation(
    authViewModel: AuthViewModel,
    navController: NavHostController
){

    val currentUser = authViewModel.currentUser.collectAsState()

    val startGraph = if (currentUser.value==null) {
        Log.i("Current User: ", "User $currentUser")
        "auth_graph"
    } else {
        Log.i("Current User: ", "User $currentUser")
        "home_graph"
    }

    NavHost(
        navController = navController,
        startDestination = startGraph
    ){
        navigation(
            startDestination = "login_screen",
            route = "auth_graph"
        ){
            composable(
                route = "login_screen"
            ) {
                LogInScreen(
                    authViewModel = authViewModel,
                    navController = navController
                )
            }

            composable(
                route = "create_user_screen"
            ){
                SignUpScreen(
                    authViewModel = authViewModel,
                    navController = navController
                )
            }
        }

        navigation(
            startDestination = "home_screen",
            route = "home_graph"
        ){
            composable(
                route = "home_screen"
            ) {
                HomeScreen(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }
        }
    }
}