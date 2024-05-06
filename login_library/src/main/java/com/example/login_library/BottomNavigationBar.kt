package com.example.login_library

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@RequiresApi(64)
@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(backgroundColor= Color.LightGray ){
                val navBackStackEntry by
                navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                NavBarItem().NavBarItems().forEach { navItem ->
                    BottomNavigationItem(
                        icon = {
                            when (navItem.route) {
                                Routes.HomePage.value -> Icon(
                                    painter = painterResource(id = R.drawable.homebutton),
                                    contentDescription = "Home",
                                    tint = Color.Unspecified  // Use the SVG's original colors
                                )
                                Routes.Profile.value -> Icon(
                                    painter = painterResource(id = R.drawable.profilebutton),
                                    contentDescription = "Profile",
                                    tint = Color.Unspecified  // Use the SVG's original colors
                                )
                                Routes.Exercise.value -> Icon(
                                    painter = painterResource(id = R.drawable.exercisebutton),
                                    contentDescription = "Exercise",
                                    tint = Color.Unspecified  // Use the SVG's original colors
                                )
                            }
                        },
                        label = { Text(navItem.label) },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == navItem.route
                        } == true,
                        onClick = {
                            navController.navigate(navItem.route) {
                                // popUpTo is used to pop up to a given destination before navigating
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                //at most one copy of a given destination on the top of the back stack
                                launchSingleTop = true
                                // this navigation action should restore any state previously saved
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController,
            startDestination = Routes.HomePage.value,
            Modifier.padding(paddingValues)
        ) {
            composable(Routes.HomePage.value) {
                HomePage(navController)
            }
            composable(Routes.Profile.value) {
                true            }
            composable(Routes.Exercise.value) {
                true
            }
        }
    }
}

@RequiresApi(64)
@Preview(showBackground = true)
@Composable
fun PreviewBottomNavigationBar() {
    BottomNavigationBar()
}
