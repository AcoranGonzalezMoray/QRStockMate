package com.example.qrstockmateapp

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

            if (isCameraPermissionGranted()) {

            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    PERMISSION_CAMERA_REQUEST
                )
            }
        setContent {
            val navController = rememberNavController()
            // Verificar si hay un token y un usuario almacenados
            val savedToken = sharedPreferences.getString(KEY_TOKEN, null)
            val savedUserJson = sharedPreferences.getString(KEY_USER, null)
            Log.d("savedToken", "${savedToken}, ${savedUserJson}")
            if (!savedToken.isNullOrBlank() && !savedUserJson.isNullOrBlank()) {
                val savedUser = Gson().fromJson(savedUserJson, User::class.java)
                navigateToBottomScreen(navController,savedUser,savedToken,::saveTokenAndUser,sharedPreferences)
            } else {
                NavigationContent(navController,::saveTokenAndUser,sharedPreferences)
            }
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
    private fun saveTokenAndUser(token: String, user: User) {
        Log.d("LLAVE","${user}")
        with(sharedPreferences.edit()) {
            putString(KEY_TOKEN, token)
            putString(KEY_USER, Gson().toJson(user))
            apply()
        }
    }
    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val PERMISSION_CAMERA_REQUEST = 1
        private const val KEY_TOKEN = "TOKEN_KEY"
        private const val KEY_USER = "USER_KEY"
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun navigateToBottomScreen(
    navController: NavHostController,
    savedUser: User,
    savedToken: String,
    onSaveTokenAndUser: (String, User) -> Unit,
    sharedPreferences: SharedPreferences
) {
    NavHost(navController = navController, startDestination = "bottomScreen"){
        // Pantallas de Autenticacion
        composable("login") {
            Login(navController = navController) { loggedIn,user,token ->
                if (loggedIn) {
                    // Ejecutar la navegación en el hilo principal
                    CoroutineScope(Dispatchers.Main).launch {
                        //se guarda aqui shared prefe
                        Initializaton(user = user, token=token)
                        onSaveTokenAndUser(token, user)
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
            BottomNavigationScreen(navController,sharedPreferences)
        }
    }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.Main) {
            Initializaton(user = savedUser, token = savedToken)
            navController.navigate("bottomScreen")
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationContent(
    navController: NavHostController,
    onSaveTokenAndUser: (String, User) -> Unit,
    sharedPreferences: SharedPreferences
) {
    Navigation(navController,onSaveTokenAndUser,sharedPreferences)
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navController: NavHostController, onSaveTokenAndUser: (String, User) -> Unit,sharedPreferences: SharedPreferences) {
    NavHost(navController = navController, startDestination = "login") {
        // Pantallas de Autenticacion
        composable("login") {
            Login(navController = navController) { loggedIn,user,token ->
                if (loggedIn) {
                    // Ejecutar la navegación en el hilo principal
                    CoroutineScope(Dispatchers.Main).launch {
                        //se guarda aqui shared prefe
                        Initializaton(user = user, token=token)
                        onSaveTokenAndUser(token, user)
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
            BottomNavigationScreen(navController,sharedPreferences)
        }
    }
}

suspend fun Initializaton(user: User, token:String){

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
            if(employees!=null){
                DataRepository.setEmployees(employees)
                employees.find { it.id == user.id }?.let { DataRepository.setUser(it) }
            }
        } else Log.d("compnayError", "error")
    }

}

