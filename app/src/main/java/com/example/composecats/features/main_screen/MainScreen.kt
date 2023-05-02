package com.example.composecats.features.main_screen

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.example.composecats.core.navigation.AppNavGraph
import com.example.composecats.core.navigation.rememberNavigationState
import com.example.composecats.features.cats_detail.presentation.CatDetailScreen
import com.example.composecats.features.feed_cats.presentation.CatsFeedScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()
    Scaffold(
    ) {
        AppNavGraph(
            navHostController = navigationState.navHostController,
            feedScreenContent = {
                CatsFeedScreen(
                    navigationState
                )
            },
            detailScreenContent = { catId ->
                CatDetailScreen(
                    navigationState,
                    catId
                )
            }
        )
    }
}

