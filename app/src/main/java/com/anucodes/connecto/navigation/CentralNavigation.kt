package com.anucodes.connecto.navigation

import androidx.compose.foundation.layout.PaddingValues
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
import com.anucodes.connecto.presentation.screens.home.UserProfileScreen


@Composable
fun CentralNavigation(
    innerPadding: PaddingValues,
    authViewModel: AuthViewModel,
    navController: NavHostController
){

    val currentUser = authViewModel.currentUser.collectAsState()

    val startGraph = if (currentUser.value==null) {
        "auth_graph"
    } else {
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
                    innerPadding = innerPadding,
                    authViewModel = authViewModel,
                    navController = navController
                )
            }

            composable(
                route = "create_user_screen"
            ){
                SignUpScreen(
                    innerPadding = innerPadding,
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

            composable(
                route = "user_profile"
            ) {
                UserProfileScreen(
                    innerPadding = innerPadding,
                    navController = navController,
                    authViewModel = authViewModel
                )
            }
        }
    }
}