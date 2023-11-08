package com.example.qrstockmateapp.screens.Auth.Login

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.qrstockmateapp.R
import com.example.qrstockmateapp.screens.Auth.ForgotPassword.ForgotPassword

@Composable
fun Login(navController: NavHostController, onLoginSuccess: (Boolean) -> Unit) {
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
    // Evento al presionar el botón de inicio de sesión
    val onLoginClicked: () -> Unit = {
        // Aquí iría la lógica de autenticación, como verificar las credenciales
        // Puedes hacer llamadas a tu ViewModel para autenticar al usuario con los datos ingresados

        // Por ejemplo, aquí simulamos un inicio de sesión exitoso si se ingresa "email" y "password"
        val isLoginSuccessful = email == "email" && password == "password"

        // Devolver el resultado al callback proporcionado
        onLoginSuccess(isLoginSuccessful)
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
