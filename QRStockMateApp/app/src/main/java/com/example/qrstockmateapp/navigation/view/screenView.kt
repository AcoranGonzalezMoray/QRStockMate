package com.example.qrstockmateapp.navigation.view

import android.annotation.SuppressLint
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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.qrstockmateapp.api.models.User
import com.example.qrstockmateapp.navigation.logic.Navigation
import com.example.qrstockmateapp.navigation.model.ScreenModel
import com.example.qrstockmateapp.navigation.repository.DataRepository
import com.example.qrstockmateapp.navigation.widget.BottomBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomNavigationScreen(navControllerLogin: NavController) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()


    val navController = rememberNavController()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerGesturesEnabled = true,

        drawerContent = {
            Drawer(

                item = ScreenModel().screensInHomeFromBottomNav,

                navController = navController,
                navControllerLogin = navControllerLogin,
                scope = scope,
                scaffoldState = scaffoldState

            )
        },
        topBar = {
            TopAppBar(
                backgroundColor = Color.Black,
                title = {
                    Text(text = "QRSTOCKMATE",color = Color.White)},
                navigationIcon = {

                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    })

                    {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "",
                            tint = Color.White)
                    }
                }

            )


        },

        bottomBar = {
            BottomBar(
                screens = ScreenModel().screensInHomeFromBottomNav,
                navController = navController,

                )

        },
    ) {
        Navigation(navController = navController)
    }


}


@Composable
fun Drawer(
    item: List<ScreenModel.HomeScreens>,

    navController: NavController,
    navControllerLogin: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black) ,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.height(40.dp)
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text("${DataRepository.getCompany()?.name}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color= Color.White)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        item.forEach() { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        navController.navigate(item.route)
                        // Closenav drawer
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                        // Close drawer


                    })
                    .height(45.dp)

                    .padding(start = 10.dp)
            ) {
                Icon(imageVector = item.icon, contentDescription = "", tint = Color.White)
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = item.title,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }


        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if(DataRepository.getUser()?.role==0) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            navController.navigate("addWarehouse")
                            scope.launch { scaffoldState.drawerState.close() }

                        })
                        .height(45.dp)

                        .padding(start = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "",
                        tint = Color.Green
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = "Add New Warehouse",
                        fontSize = 18.sp,
                        color = Color.Green
                    )

                }
            }
            if(DataRepository.getUser()?.role==0 || DataRepository.getUser()?.role==1) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            navController.navigate("manageUser")
                            scope.launch { scaffoldState.drawerState.close() }

                        })
                        .height(45.dp)

                        .padding(start = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "",
                        tint = Color.Yellow
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = "Manage User",
                        fontSize = 18.sp,
                        color = Color.Yellow
                    )

                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        DataRepository.LogOut()
                        navControllerLogin.navigate("login")
                        scope.launch { scaffoldState.drawerState.close() }

                    })
                    .height(45.dp)

                    .padding(start = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "",
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = "Log Out",
                    fontSize = 18.sp,
                    color = Color.Red
                )

            }
        }

    }


}
