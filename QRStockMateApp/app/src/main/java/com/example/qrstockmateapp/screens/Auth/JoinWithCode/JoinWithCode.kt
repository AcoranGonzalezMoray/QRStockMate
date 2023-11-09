package com.example.qrstockmateapp.screens.Auth.JoinWithCode

import android.os.Handler
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.qrstockmateapp.api.models.Company
import com.example.qrstockmateapp.api.models.User
import com.example.qrstockmateapp.api.services.RegistrationBody
import com.example.qrstockmateapp.api.services.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.widget.Toast
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.core.content.ContentProviderCompat.requireContext

@Composable
fun JoinWithCodeScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordMatches by remember { mutableStateOf(true) }
    val scaffoldState = rememberScaffoldState()
    var phone by remember { mutableStateOf("") }


    var code by remember { mutableStateOf("") }

    val keyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    )
    val customTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        cursorColor = Color.Black,
        focusedBorderColor = Color.Black,
        focusedLabelColor = Color.Black,
        unfocusedBorderColor = Color.Black,
        backgroundColor = Color.LightGray
    )
    val onJoin:() -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                if(code.isEmpty()) code = "000-000"
                val user = User(0,name, email, password,phone,code,"",3)
                val company = Company(0,"","","","","","") //No se va a usar
                val model  = RegistrationBody(user,company)
                val response = RetrofitInstance.api.joinWithCode(model)
                if (response.isSuccessful) {
                    val joinResponse = response.body()
                    if (joinResponse != null) {
                        scaffoldState.snackbarHostState.showSnackbar("You have successfully joined. Please sign in.")

                        // Redirigir a la pantalla de inicio de sesión después de 5 segundos
                        Handler().postDelayed({
                            navController.navigate("login")
                        }, 5000)
                    } else {
                        scaffoldState.snackbarHostState.showSnackbar("Verify that the code is correct, and that the email is new in the app\n")
                        Log.d("excepcionUserA","")
                    }
                }else{
                    scaffoldState.snackbarHostState.showSnackbar("Verify that the code is correct, and that the email is new in the app\n")
                    Log.d("excepcionUserB","")
                }
            }catch (e: Exception) {
                Log.d("excepcionUserC","${e}")
                scaffoldState.snackbarHostState.showSnackbar("Verify that the code is correct, and that the email is new in the app\n")

            }
        }

    }

    Column(
        modifier = Modifier.padding(16.dp) ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally // Alineación central horizontal
    ) {
        Text(
            text = "Join With Code",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = confirmPassword,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = {
                confirmPassword = it
                passwordMatches = password == it
            },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors
        )
        if (!passwordMatches) {
            Text("Passwords do not match", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = code,
            onValueChange = {
                if (it.length <= 7 && it.matches(Regex("[A-Za-z0-9-]*"))) {
                    val sanitized = it.filter { it.isLetterOrDigit() }.uppercase()
                    if (sanitized.length > 3) {
                        code = "${sanitized.substring(0, 3)}-${sanitized.substring(3)}"
                    } else {
                        code = sanitized
                    }
                }
            },
            label = { Text("Code") },
            visualTransformation = VisualTransformation.None,
            keyboardOptions = keyboardOptions,
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors
        )

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { navController.navigate("login")},
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text("Cancel", color = Color.White)
            }

            Button(
                onClick = {onJoin()},
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text("Join", color = Color.White)
            }
        }
    }
}
