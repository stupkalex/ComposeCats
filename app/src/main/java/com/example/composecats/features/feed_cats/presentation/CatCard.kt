package com.example.composecats.features.feed_cats.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composecats.core.entity.CatEntity
import com.example.composecats.R

@Composable
fun CatCard(
    catModel: CatEntity,
    favouriteClickListener: (CatEntity) -> Unit,
    detailClickListener: (String) -> Unit,
    iconResId: Int,
    tint: Color,
    viewModel: FeedViewModel
) {
    Card(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(8.dp),
        elevation = 3.dp
    ) {
        Column() {
            val placeholder = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground)
            val url = if (catModel.isDownload) catModel.localUrl else catModel.networkUrl
            AsyncImage(
                model = url,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(catModel.ratio,true )
                    .clickable {
                        detailClickListener(catModel.id)
                    },
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                placeholder = rememberVectorPainter(image = placeholder),
                onSuccess = {
                    if(!catModel.isDownload)
                    viewModel.saveToCache(it.result.drawable, catModel)
                }
            )
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
                    .clickable {
                        favouriteClickListener.invoke(catModel)
                    },
                painter = painterResource(id = iconResId),
                contentDescription = null,
                tint = tint
            )

        }
    }
}
