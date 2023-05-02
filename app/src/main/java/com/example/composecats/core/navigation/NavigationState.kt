package com.example.composecats.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(
    val navHostController: NavHostController
) {

    fun navigateToFeed() {
        navHostController.navigate(Screen.Feed.route) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
         /*   launchSingleTop = true
            restoreState = true*/
        }
    }

    fun navigateToDetail(catId: String) {
        navHostController.navigate(Screen.Detail.getRouteWithArgs(catId)) // comments/15
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}