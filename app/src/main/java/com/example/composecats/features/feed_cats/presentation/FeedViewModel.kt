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
import com.example.composecats.features.feed_cats.domain.usecases.ShowAllCatsUseCase
import com.example.composecats.features.feed_cats.domain.usecases.ShowFavouriteCatsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val getCatsUseCase: GetCatsUseCase,
    private val showAllCatsUseCase: ShowAllCatsUseCase,
    private val showFavouriteCatsUseCase: ShowFavouriteCatsUseCase,
    private val addFavouriteCatsUseCase: AddCatToFavouriteUseCase,
    private val deleteCatToFavouriteUseCase: DeleteCatToFavouriteUseCase
) : ViewModel() {

    var catsList : MutableStateFlow<List<CatEntity>>
    init {
        catsList = MutableStateFlow(emptyList())
        getCatsList()
    }



    private fun getCatsList() {
        viewModelScope.launch {
            getCatsUseCase.invoke()
                .collect { patch ->
                    if (!patch.content.isNullOrEmpty()) {
                        when (patch.type) {
                            UpdateDataSet -> {
                                catsList.emit(patch.content.toMutableList())
                            }
                            NotifyItemChanged -> {

                            }
                            LoadMore -> {
                                catsList.emit(patch.content)
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
        }
    }

    fun showFavouriteCats() {
        viewModelScope.launch {
            showFavouriteCatsUseCase.invoke()
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
}