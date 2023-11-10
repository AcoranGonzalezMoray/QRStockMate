package com.example.qrstockmateapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qrstockmateapp.api.models.User
import com.example.qrstockmateapp.api.services.RetrofitInstance
import com.example.qrstockmateapp.navigation.repository.DataRepository
import com.example.qrstockmateapp.navigation.view.BottomNavigationScreen
import com.example.qrstockmateapp.screens.Auth.ForgotPassword.ForgotPassword
import com.example.qrstockmateapp.screens.Auth.JoinWithCode.JoinWithCodeScreen
import com.example.qrstockmateapp.screens.Auth.Login.Login
import com.example.qrstockmateapp.screens.Auth.SignUp.SignUpScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
            Login(navController = navController) { loggedIn,user,token ->
                if (loggedIn) {
                    // Ejecutar la navegación en el hilo principal
                    CoroutineScope(Dispatchers.Main).launch {
                        Initializaton(user = user, token=token)
                        navController.navigate("bottomScreen")
                    }
                }
            }
        }

        composable("forgotPassword"){
            ForgotPassword(navController = navController) {}
        }

        composable("joinWithCode"){
            JoinWithCodeScreen(navController = navController)
        }

        composable("signUp"){
            SignUpScreen(navController = navController)
        }

        //Aplicacion Con sus Funciones
        composable("bottomScreen") {
            BottomNavigationScreen(navController)
        }
    }
}

suspend fun Initializaton(user: User, token:String){
    DataRepository.setUser(user)
    DataRepository.setToken(token)

    val companyResponse = RetrofitInstance.api.getCompanyByUser(user)
    if (companyResponse.isSuccessful) {
        val company = companyResponse.body()
        if(company!=null)DataRepository.setCompany(company)
    } else Log.d("compnayError", "error")


    val company = DataRepository.getCompany()
    if(company!=null){
        val employeesResponse = RetrofitInstance.api.getEmployees(company)
        if (employeesResponse.isSuccessful) {
            val employees = employeesResponse.body()
            Log.d("EMPLOYEE", "${employees}")
            if(employees!=null)DataRepository.setEmployees(employees)
        } else Log.d("compnayError", "error")
    }

}

