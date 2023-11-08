package com.example.qrstockmateapp.navigation.widget

import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.qrstockmateapp.navigation.model.ScreenModel

@Composable

fun BottomBar(
    modifier: Modifier = Modifier,
    screens: List<ScreenModel.HomeScreens>,
    navController: NavController,

    ) {
    BottomNavigation {
        val backStackEntry = navController.currentBackStackEntryAsState()
        screens.forEach {


                screens ->
            val currentRoute = backStackEntry.value?.destination?.route;
            val selected = currentRoute == screens.route

            BottomNavigationItem(
                modifier = Modifier.background(Color.White),
                icon = {
                    Icon(
                        imageVector = screens.icon,
                        contentDescription = "",
                        tint = if (selected) Color.Blue else Color.Black
                    )
                },
                selected = selected,
                label = {
                    Text(
                        if (selected) screens.title else "", // Label
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Blue
                    )
                },

                onClick = {
                    if (currentRoute != screens.route) {
                        navController.navigate(screens.route)

                    }

                }

            )
        }

    }

}