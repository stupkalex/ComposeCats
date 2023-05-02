package com.example.composecats.features.feed_cats.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composecats.core.Patch
import com.example.composecats.core.PatchType.*
import com.example.composecats.core.entity.CatEntity
import com.example.composecats.features.favourite.domain.usecases.AddCatToFavouriteUseCase
import com.example.composecats.features.favourite.domain.usecases.DeleteCatToFavouriteUseCase
import com.example.composecats.features.feed_cats.domain.usecases.GetCatsUseCase
import com.example.composecats.features.feed_cats.domain.usecases.LoadMoreUseCase
import com.example.composecats.features.feed_cats.domain.usecases.ShowAllCatsUseCase
import com.example.composecats.features.feed_cats.domain.usecases.ShowFavouriteCatsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGING_OFFSET = 2

class FeedViewModel @Inject constructor(
    private val getCatsUseCase: GetCatsUseCase,
    private val showAllCatsUseCase: ShowAllCatsUseCase,
    private val showFavouriteCatsUseCase: ShowFavouriteCatsUseCase,
    private val addFavouriteCatsUseCase: AddCatToFavouriteUseCase,
    private val deleteCatToFavouriteUseCase: DeleteCatToFavouriteUseCase,
    private val loadMoreUseCase: LoadMoreUseCase
) : ViewModel() {

    var catsList: MutableStateFlow<List<CatEntity>>

    private var listSize = 0
    private var uploadImageCount = 0

    init {
        catsList = MutableStateFlow(emptyList())
        getCatsList()
    }

    var nextDataIsLoading = MutableStateFlow(false)
    var showFavourite = MutableStateFlow(false)



    private fun getCatsList() {
        viewModelScope.launch {
            getCatsUseCase.invoke()
                .collect { patch ->
                    if (!patch.content.isNullOrEmpty()) {
                        when (patch.type) {
                            UpdateDataSet -> {
                                catsList.emit(patch.content.toMutableList())
                                listSize = patch.content.size
                            }
                            LoadMore -> {
                                catsList.emit(patch.content)
                                listSize = patch.content.size
                                nextDataIsLoading.emit(true)
                            }
                            ErrorRequest -> {}
                        }
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
            loadMoreUseCase.invoke()
        }
    }

}