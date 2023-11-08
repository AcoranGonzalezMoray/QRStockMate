package com.example.qrstockmateapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qrstockmateapp.navigation.view.BottomNavigationScreen
import com.example.qrstockmateapp.screens.Auth.ForgotPassword.ForgotPassword
import com.example.qrstockmateapp.screens.Auth.Login.Login
import com.example.qrstockmateapp.ui.theme.QRStockMateAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavigationContent(navController)
        }
    }
}

@Composable
fun NavigationContent(navController: NavHostController) {
    Navigation(navController)
}


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        // Pantallas de Autenticacion
        composable("login") {
            Login(navController = navController) { loggedIn ->
                if (loggedIn) {
                    navController.navigate("bottomScreen")
                }
            }
        }

        composable("forgotPassword"){
            ForgotPassword(navController = navController) {

            }
        }

        //Aplicacion Con sus Funciones
        composable("bottomScreen") {
            BottomNavigationScreen()
        }
    }
}


