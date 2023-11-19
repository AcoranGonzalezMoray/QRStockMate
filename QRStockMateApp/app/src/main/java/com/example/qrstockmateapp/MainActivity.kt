package com.example.qrstockmateapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
            if (isCameraPermissionGranted()) {
                setContent {
                    val navController = rememberNavController()
                    NavigationContent(navController)
                }
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    PERMISSION_CAMERA_REQUEST
                )
            }

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CAMERA_REQUEST) {
            if (isCameraPermissionGranted()) {
                // start camera
            } else {
                Log.e(TAG, "no camera permission")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            baseContext,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val PERMISSION_CAMERA_REQUEST = 1
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

