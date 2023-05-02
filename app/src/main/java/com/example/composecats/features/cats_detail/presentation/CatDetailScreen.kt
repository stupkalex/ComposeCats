package com.example.composecats.features.cats_detail.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.composecats.R
import com.example.composecats.core.application.getApplicationComponent
import com.example.composecats.core.entity.CatEntity
import com.example.composecats.core.navigation.NavigationState
import com.example.composecats.ui.theme.DarkRed

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CatDetailScreen(
    navigationState: NavigationState,
    catId: String
) {
    val component = getApplicationComponent().getDetailScreenComponentFactory().create(catId)
    val viewModel: DetailViewModel = viewModel(factory = component.getViewModelFactory())
    val catModel = viewModel.getCat.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.background(MaterialTheme.colors.secondary),
                title = {
                    Text(
                        text = "CatDetail",
                        color = MaterialTheme.colors.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigationState.navigateToFeed()
                        }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }


                }
            )
        },
        content = {
            catModel.value?.let {

                val iconResId = if (it.isFavourite) R.drawable.ic_like_set else R.drawable.ic_like
                val tint = if (it.isFavourite) DarkRed else MaterialTheme.colors.onSecondary

                CatDetailCard(
                    catModel = it,
                    viewModel = viewModel,
                    iconResId = iconResId,
                    tint = tint
                )
            }
        }
    )


}

@Composable
fun CatDetailCard(
    catModel: CatEntity,
    viewModel: DetailViewModel,
    iconResId: Int,
    tint: Color
) {
    Column() {
        AsyncImage(
            model = catModel.networkUrl,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )
        Icon(
            modifier = Modifier
                .size(40.dp)
                .padding(8.dp)
                .clickable {
                    viewModel.favouriteClick(catModel)
                },
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = tint
        )
    }
}