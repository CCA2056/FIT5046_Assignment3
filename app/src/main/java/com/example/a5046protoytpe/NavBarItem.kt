package com.example.a5046protoytpe

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class NavBarItem (
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {
    fun NavBarItems(): List<NavBarItem> {
        return listOf(
            NavBarItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = Routes.HomePage.value
            ),
            NavBarItem(
                label = "Profile",
                icon = Icons.Filled.AccountCircle,
                route = Routes.Profile.value
            ),
            NavBarItem(
                label = "Exercise",
                icon = Icons.Filled.Person,
                route = Routes.Exercise.value
            ),
        )
    }
}