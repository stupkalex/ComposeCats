package com.example.composecats.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.composecats.core.entity.CatEntity
import com.example.composecats.core.navigation.Screen.Companion.KEY_CAT_ID

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    detailScreenContent: @Composable (String) -> Unit
) {
    navigation(
        startDestination = Screen.Feed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.Feed.route) {
            newsFeedScreenContent()
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument(KEY_CAT_ID) {
                    type = CatEntity.NavigationType

                }
            )
        ) {
            val catId = it.arguments?.getString(KEY_CAT_ID)
                ?: throw RuntimeException("Args is null")
            detailScreenContent(catId)
        }
    }

}
