package com.example.qrstockmateapp.navigation.logic

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.qrstockmateapp.screens.Auth.ForgotPassword.ForgotPassword
import com.example.qrstockmateapp.screens.Auth.Login.Login
import com.example.qrstockmateapp.screens.Home.HomeScreen
import com.example.qrstockmateapp.screens.Profile.ProfileScreen
import com.example.qrstockmateapp.screens.ScanQR.ScanScreen
import com.example.qrstockmateapp.screens.Search.SearchScreen


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen()
        }
        composable("search") {
            SearchScreen()
        }
        composable("profile") {
            ProfileScreen()
        }
        composable("scan") {
            ScanScreen()
        }
    }
}