package com.example.qrstockmateapp.screens.Home.UpdateWarehouse


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.qrstockmateapp.R
import com.example.qrstockmateapp.api.models.User
import com.example.qrstockmateapp.api.services.RetrofitInstance
import com.example.qrstockmateapp.navigation.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UpdateWarehouseScreen(navController: NavController) {
    var warehouse = remember { DataRepository.getWarehousePlus() }
    var selectedOption by remember { mutableStateOf("Selected an existing administrator to associate with the warehouse") }


    var isMenuExpanded by remember { mutableStateOf(false) }
    var employees = remember { DataRepository.getEmployees()?.filter { it.role == 1 } ?: emptyList() }

    LaunchedEffect(Unit){
        if(employees!=null && warehouse!=null)selectedOption= "Name: ${employees.find { user: User ->  user.id == warehouse.idAdministrator}?.name}  Role: Administrator Code: ${employees.find { user: User ->  user.id == warehouse.idAdministrator}?.code};${employees.find { user: User ->  user.id == warehouse.idAdministrator}?.id}"
    }

    val customTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        cursorColor = Color.Black,
        focusedBorderColor = Color.Black,
        focusedLabelColor = Color.Black,
        unfocusedBorderColor = Color.Black,
        backgroundColor = Color.LightGray
    )


    val updateWarehouse : () -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                if(warehouse!=null){
                    Log.d("excepcionWarehouseCambio","${warehouse}")
                    val response =  RetrofitInstance.api.updateWarehouse(warehouse)

                    if (response.isSuccessful) {
                        val wResponse = response.body()
                        withContext(Dispatchers.Main){
                            navController.navigate("home")
                        }
                    }else{
                    }
                }
            }catch (e: Exception) {
                Log.d("excepcionWarehouse","${e}")
            }
        }

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Sección de la imagen (ocupará la mitad de la pantalla)
        if(warehouse!=null && !warehouse.url.isNullOrBlank()){
            val painter = rememberImagePainter(
                data = warehouse.url,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.user)
                }
            )
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
            )
        }else{
            Image(
                painter = painterResource(id = R.drawable.warehouse), // Reemplaza con tu lógica para cargar la imagen
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f) // La imagen ocupa la mitad de la pantalla
            )
        }
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            warehouse?.let {
                var name by remember { mutableStateOf(it.name) }
                var location by remember { mutableStateOf(it.location) }
                var organization by remember { mutableStateOf(it.organization) }
                var administratorId by remember { mutableStateOf(it.idAdministrator) }

                TextField(
                    value = name,
                    label = { Text("Name") },
                    onValueChange = { name = it },
                    colors= customTextFieldColors,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
                TextField(
                    value = location,
                    label = { Text("Location") },
                    onValueChange = { location = it },
                    colors= customTextFieldColors,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
                TextField(
                    value = organization,
                    label = { Text("Organization") },
                    onValueChange = { organization = it },
                    colors= customTextFieldColors,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = selectedOption,
                        modifier = Modifier
                            .background(Color.LightGray)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                isMenuExpanded = true
                            }
                            .padding(16.dp)
                    )

                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        employees?.forEach { employee ->
                            DropdownMenuItem(onClick = {
                                selectedOption= "Name: ${employee.name}  Role: Administrator Code: ${employee.code};${employee.id}"
                                administratorId = employee.id
                                isMenuExpanded = false
                            }) {
                                Text("Name: ${employee.name}  Role: Administrator Code: ${employee.code}" )
                            }
                        }
                    }
                }
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    warehouse.name =name
                    warehouse.location = location
                    warehouse.organization = organization
                    warehouse.idAdministrator = administratorId

                    updateWarehouse()
                } ) {
                    Text(text = "Update")
                }
            }
        }

    }
}
