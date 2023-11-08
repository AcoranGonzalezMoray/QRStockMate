package com.example.qrstockmateapp.screens.Auth.Login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.qrstockmateapp.R
import com.example.qrstockmateapp.api.models.User
import com.example.qrstockmateapp.api.services.ApiService
import com.example.qrstockmateapp.api.services.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun Login(navController: NavHostController, onLoginSuccess: (Boolean, User, String) -> Unit) {
    //onLoginSuccess: (Boolean) -> Unit
    // Aquí podrías definir los estados de los campos del formulario de inicio de sesión
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    val customTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        cursorColor = Color.Black,
        focusedBorderColor = Color.Black,
        focusedLabelColor = Color.Black,
        unfocusedBorderColor = Color.Black,
        backgroundColor = Color.LightGray
    )

    val onLoginClicked: () -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.signIn(email, password)
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        val user = loginResponse.user
                        val token = loginResponse.token
                        // Hacer lo que necesites con el usuario y el token
                        onLoginSuccess(true, user, token)
                    } else {
                        Log.d("errorUser", "error es null")
                    }
                } else {
                    // Manejar la respuesta de error (códigos de estado diferentes de 200)
                    Log.d("errorCode", "Código de error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.d("excepcionUser","${e}")
            }
        }
    }





    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(26.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.icon),
            contentDescription = "Descripción de la imagen",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
            )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Botón para iniciar sesión
        Button(onClick = onLoginClicked,
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier.fillMaxWidth()) {
            Text("Login")
        }

        Row {
            Text(
                text = "Forgot password",
                color = Color.Gray,
                modifier = Modifier
                    .clickable {
                        navController.navigate("forgotPassword")
                    }
                    .padding(5.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Join with code",
                color = Color.Blue,
                modifier = Modifier
                    .clickable {

                    }
                    .padding(5.dp)
            )
            Text(
                text = "Sign up",
                color = Color.Blue,
                modifier = Modifier
                    .clickable {

                    }
                    .padding(5.dp)
            )
        }
    }
}
