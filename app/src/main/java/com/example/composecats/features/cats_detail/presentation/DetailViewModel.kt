package com.example.composecats.features.cats_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composecats.core.local.entity.CatEntity
import com.example.composecats.features.cats_detail.domain.usercases.GetCatByIdUseCase
import com.example.composecats.features.favourite.domain.usecases.AddCatToFavouriteUseCase
import com.example.composecats.features.favourite.domain.usecases.DeleteCatToFavouriteUseCase
import com.example.composecats.core.image_cache.domain.usecases.SaveImageToDownloadsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getCatByIdUseCase: GetCatByIdUseCase,
    private val addFavouriteCatsUseCase: AddCatToFavouriteUseCase,
    private val deleteCatToFavouriteUseCase: DeleteCatToFavouriteUseCase,
    private val saveImageToDownloadsUseCase: SaveImageToDownloadsUseCase,
    private val catId: String
): ViewModel() {

    val getCat = MutableStateFlow<CatEntity?>(null)
    var currentCat: CatEntity? = null

    init {
        getCat()
    }
    private fun getCat() {
        viewModelScope.launch {
           currentCat = getCatByIdUseCase.invoke(catId)
           getCat.emit(currentCat)
        }
    }

    fun favouriteClick(cat: CatEntity) {
        viewModelScope.launch {
            if (cat.isFavourite) {
                deleteCatToFavouriteUseCase.invoke(cat)
            } else {
                addFavouriteCatsUseCase.invoke(cat)
            }
            currentCat?.let{
                getCat.emit(it.copy(isFavourite = !cat.isFavourite))
            }

        }
    }

    fun download(cat: CatEntity) {
        saveImageToDownloadsUseCase.invoke(cat)
    }
}