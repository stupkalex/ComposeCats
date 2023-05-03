package com.example.composecats.features.feed_cats.presentation

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composecats.core.PatchType.*
import com.example.composecats.core.entity.CatEntity
import com.example.composecats.features.favourite.domain.usecases.AddCatToFavouriteUseCase
import com.example.composecats.features.favourite.domain.usecases.DeleteCatToFavouriteUseCase
import com.example.composecats.features.feed_cats.domain.usecases.GetCatsUseCase
import com.example.composecats.features.feed_cats.domain.usecases.LoadMoreUseCase
import com.example.composecats.features.feed_cats.domain.usecases.ShowAllCatsUseCase
import com.example.composecats.features.feed_cats.domain.usecases.ShowFavouriteCatsUseCase
import com.example.composecats.features.save_images.domain.usecases.SaveImageToCacheUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val getCatsUseCase: GetCatsUseCase,
    private val showAllCatsUseCase: ShowAllCatsUseCase,
    private val showFavouriteCatsUseCase: ShowFavouriteCatsUseCase,
    private val addFavouriteCatsUseCase: AddCatToFavouriteUseCase,
    private val deleteCatToFavouriteUseCase: DeleteCatToFavouriteUseCase,
    private val loadMoreUseCase: LoadMoreUseCase,
    private val saveImageToCacheUseCase: SaveImageToCacheUseCase
) : ViewModel() {

    var catsList: MutableStateFlow<List<CatEntity>>

    val showFavourite by lazy {
        MutableStateFlow(false)
    }

    val nextDataIsLoading by lazy {
        MutableStateFlow(false)
    }

    init {
        catsList = MutableStateFlow(emptyList())
        getCatsList()
    }

    fun saveToCache(drawable: Drawable,cat: CatEntity) {
       saveImageToCacheUseCase.invoke(drawable, cat)
    }

    private fun getCatsList() {
        viewModelScope.launch {
            getCatsUseCase.invoke()
                .collect { patch ->
                        when (patch.type) {
                            UpdateDataSet -> {
                                if (!patch.content.isNullOrEmpty()) {
                                    catsList.emit(patch.content.toMutableList())
                                }
                                nextDataIsLoading.emit(false)
                            }
                            LoadMore -> {
                                if (!patch.content.isNullOrEmpty()) {
                                catsList.emit(patch.content)
                                nextDataIsLoading.emit(false)
                                }
                            }
                            ErrorRequest -> {}
                        }
                }
        }

    }

    fun showAllCats() {
        viewModelScope.launch {
            showAllCatsUseCase.invoke()
            showFavourite.emit(false)
        }
    }

    fun showFavouriteCats() {
        viewModelScope.launch {
            showFavouriteCatsUseCase.invoke()
            showFavourite.emit(true)
        }
    }

    fun favouriteClick(cat: CatEntity) {
        viewModelScope.launch {
            if (cat.isFavourite) {
                deleteCatToFavouriteUseCase.invoke(cat)
            } else {
                addFavouriteCatsUseCase.invoke(cat)
            }
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            nextDataIsLoading.emit(true)
            loadMoreUseCase.invoke()
        }
    }

}