package com.example.composecats.features.feed_cats.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composecats.R
import com.example.composecats.core.application.getApplicationComponent
import com.example.composecats.core.entity.CatEntity
import com.example.composecats.core.navigation.NavigationState
import com.example.composecats.core.navigation.rememberNavigationState
import com.example.composecats.ui.theme.DarkRed

@Composable
fun CatsFeedScreen(
    navigationState: NavigationState
) {
    val component = getApplicationComponent()
    val viewModel: FeedViewModel = viewModel(factory = component.getViewModelFactory())

    NewsFeedScreenContent(
        viewModel = viewModel,
        navigationState
    )
}

@Composable
private fun NewsFeedScreenContent(
    viewModel: FeedViewModel,
    navigationState: NavigationState
) {

    val posts = viewModel.catsList.collectAsState()
    val nextDataIsLoading = viewModel.nextDataIsLoading.collectAsState()
    val showFavourite = viewModel.showFavourite.collectAsState()

    FeedCats(
        viewModel = viewModel,
        posts.value,
        navigationState,
        nextDataIsLoading.value,
        showFavourite.value
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FeedCats(
    viewModel: FeedViewModel,
    posts: List<CatEntity>,
    navigationState: NavigationState,
    nextDataIsLoading: Boolean,
    showFavourite: Boolean
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.background(MaterialTheme.colors.secondary),
                title = {
                    Text(
                        text = "CatsCompose",
                        color = MaterialTheme.colors.onPrimary
                    )
                },
                actions = {
                    SelectCatsMenu(viewModel = viewModel)
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colors.background),
            contentPadding = PaddingValues(
                top = 16.dp, start = 8.dp, end = 8.dp, bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = posts, key = { it.id }) { feedPost ->

                val iconResId =
                    if (feedPost.isFavourite) R.drawable.ic_like_set else R.drawable.ic_like
                val tint =
                    if (feedPost.isFavourite) DarkRed else MaterialTheme.colors.onSecondary

                CatCard(
                    catModel = feedPost,
                    favouriteClickListener = {
                        viewModel.favouriteClick(feedPost)
                    },
                    detailClickListener = { _ ->
                        navigationState.navigateToDetail(feedPost.id)
                    },
                    iconResId,
                    tint,
                    viewModel
                )
            }
            if (!showFavourite) {
                item {
                    if (nextDataIsLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(color = DarkRed)
                        }
                    } else {
                        SideEffect {
                            viewModel.loadMore()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SelectCatsMenu(viewModel: FeedViewModel) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                viewModel.showAllCats()
                expanded = false
            }) {
                Text("AllCats")
            }
            Divider()
            DropdownMenuItem(onClick = {
                viewModel.showFavouriteCats()
                expanded = false
            }) {
                Text("FavouriteCats")
            }
        }
    }
}
