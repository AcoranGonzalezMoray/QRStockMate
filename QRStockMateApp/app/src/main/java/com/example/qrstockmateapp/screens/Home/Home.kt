package com.example.qrstockmateapp.screens.Home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.qrstockmateapp.api.models.Company
import com.example.qrstockmateapp.api.models.User
import com.example.qrstockmateapp.api.models.Warehouse
import com.example.qrstockmateapp.api.services.RetrofitInstance
import com.example.qrstockmateapp.navigation.repository.DataRepository

@Composable
fun HomeScreen(navController: NavController) {
    val user = remember { DataRepository.getUser() }

    if(user !=null){
        LaunchedEffect(Unit) {
            val company = DataRepository.getCompany()
            if(company!=null){
                val warehouseResponse = RetrofitInstance.api.getWarehouse(company)
                if (warehouseResponse.isSuccessful){
                    val warehouses = warehouseResponse.body()
                    Log.d("Warehouse", "${warehouses}")
                    if(warehouses!=null )DataRepository.setWarehouses(warehouses)
                }
            }
        }
    }

    val warehouses = remember { DataRepository.getWarehouses() }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Home screen",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
        )

        // Mostrar la lista de almacenes
        if (warehouses != null) {
            WarehouseList(warehouses)
        }else{
            Text(text = "There are no warehouses available for this company")
        }
    }
}

@Composable
fun WarehouseList(warehouses: List<Warehouse>) {
    LazyColumn {
        items(warehouses) { warehouse ->
            WarehouseItem(warehouse)
        }
    }
}

@Composable
fun WarehouseItem(warehouse: Warehouse) {
    // Muestra los detalles del almacén
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Name: ${warehouse.name}")
        Text(text = "URL: ${warehouse.url}")
        Text(text = "Location: ${warehouse.location}")
        // Otros detalles del almacén
    }
}
