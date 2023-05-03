package com.example.composecats.features.feed_cats.data

import android.util.Log
import com.example.composecats.core.Patch
import com.example.composecats.core.PatchType.*
import com.example.composecats.core.database.CatsDao
import com.example.composecats.core.entity.CatEntity
import com.example.composecats.core.network.NetworkResult.*
import com.example.composecats.core.network.dto.toEntity
import com.example.composecats.core.network.retrofit.ApiService
import com.example.composecats.features.favourite.domain.usecases.UpdateCatStateUseCase
import com.example.composecats.features.feed_cats.domain.FeedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FeedRepository"
private const val BATCH_LIMIT = 25

class FeedRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val database: CatsDao,
    private val updateCatState: UpdateCatStateUseCase
) : FeedRepository {

    private val ioCoroutineScope = CoroutineScope(Dispatchers.IO)

    private val catsHotCache = FeedHotCache()

    private var notNetworkPaging = false

    private var page = 0

    private val getCatsFlow = MutableStateFlow(
        Patch(UpdateDataSet, emptyList())
    )

    init {
        initCache(database)
        observeUpdateCat()
    }

    private fun observeUpdateCat() {
        ioCoroutineScope.launch {
            updateCatState
                .invoke()
                .collect {
                    catsHotCache.updateCache(it)
                    val patch = Patch(
                        UpdateDataSet,
                        catsHotCache.getValue()
                    )
                    getCatsFlow.emit(patch)
                }
        }
    }

    private fun initCache(database: CatsDao) {
        ioCoroutineScope.launch {
            catsHotCache.updateCache(database.getAllCats())
            val patch = Patch(
                UpdateDataSet,
                catsHotCache.getValue()
            )
            getCatsFlow.emit(patch)
        }
    }

    override suspend fun getCats(): Flow<Patch> {
        return getCatsFlow
    }

    override suspend fun showFavoriteCats() {
        notNetworkPaging = true
        val favouriteCats = database.getAllFavouriteCats()
        val patch = Patch(
            UpdateDataSet,
            favouriteCats.sortedBy { it.date }
        )
        getCatsFlow.emit(patch)
    }

    override suspend fun showAllCats() {
        notNetworkPaging = false
        val patch = Patch(
            UpdateDataSet,
            catsHotCache.getValue()
        )
        getCatsFlow.emit(patch)
    }

    override suspend fun loadMore() {
        val response = apiService.getCatsBatch(BATCH_LIMIT, page)

        val result = if (response.isSuccessful) {
            page++
            Success(response.body())
        } else {
            Error(response.message())
        }

        when (result) {
            is Success -> {
                val catsEntities = result.data?.map {
                    it.toEntity()
                }?.filter {
                    !catsHotCache.contains(it.id)
                }

                catsEntities?.let {
                    database.addCats(catsEntities)
                    catsHotCache.updateCache(it)
                    val patch = Patch(
                        UpdateDataSet,
                        catsHotCache.getValue()
                    )
                    getCatsFlow.emit(patch)
                }

            }
            is Error -> {
                Log.d(TAG, result.messageText)
                val patch = Patch(
                    ErrorRequest,
                    emptyList(),
                    true
                )
                getCatsFlow.emit(patch)
            }
        }

    }
}

class FeedHotCache() {
    private val cache: HashMap<String, CatEntity> = HashMap<String, CatEntity>()

    fun getValue() = cache.values.sortedBy { it.date }

    fun updateCache(list: List<CatEntity>) {
        cache.putAll(
            list.map {
                it.id to it
            }
        )
    }

    fun updateCache(item: CatEntity) {
        cache[item.id] = item
    }

    fun contains(id: String): Boolean = cache.containsKey(id)
}