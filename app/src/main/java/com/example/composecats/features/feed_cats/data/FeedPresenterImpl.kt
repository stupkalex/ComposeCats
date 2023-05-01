package com.example.composecats.features.feed_cats.data

import android.util.Log
import com.example.composecats.core.Patch
import com.example.composecats.core.PatchType.*
import com.example.composecats.core.database.CatsDao
import com.example.composecats.core.entity.CatEntity
import com.example.composecats.core.network.NetworkResult.*
import com.example.composecats.core.network.dto.toEntity
import com.example.composecats.core.network.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FeedRepository"
private const val BATCH_LIMIT = 25

class FeedPresenterImpl @Inject constructor(
    val apiService: ApiService,
    val database: CatsDao
) : FeedRepository {

    private val ioCoroutineScope = CoroutineScope(Dispatchers.IO)

    private val catsHotCache = HashMap<String, CatEntity>()

    private var notNetworkPaging = false

    private var page = 0

    val getCatsFlow = MutableStateFlow(
        Patch(UpdateDataSet, emptyList())
    )

    init {
        initCache(database)
    }

    private fun initCache(database: CatsDao) {
        ioCoroutineScope.launch {
            val cashCats = database.getAllCats().map {
                it.id to it
            }
            catsHotCache.putAll(cashCats)
            val patch = Patch(
                UpdateDataSet,
                catsHotCache.values.toList()
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
            favouriteCats
        )
        getCatsFlow.emit(patch)
    }

    override suspend fun showAllCats() {
        notNetworkPaging = false
        val patch = Patch(
            UpdateDataSet,
            catsHotCache.values.toList()
        )
        getCatsFlow.emit(patch)
    }

    override suspend fun loadMore() {
        val response = apiService.getCatsBatch(BATCH_LIMIT, page)
        response.map { result ->
            val testResult = if (result.isSuccessful) {
                Success(result.body())
            } else {
                Error(result.message())
            }
            testResult
        }.collect { result ->
            when (result) {
                is Success -> {
                    val catsEntities = result.data?.map{
                        it.toEntity()
                    }?.filter {
                        catsHotCache[it.id] == null
                    }

                    catsEntities?.let {
                        database.addCats(catsEntities)
                        catsHotCache.putAll(catsEntities.map {
                            it.id to it
                        })
                        val patch = Patch(
                            UpdateDataSet,
                            catsEntities
                        )
                        getCatsFlow.emit(patch)
                    }

                }
                is Error -> {
                    Log.d(TAG, result.messageText)
                    val patch = Patch(
                        ErrorRequest,
                        emptyList()
                    )
                    getCatsFlow.emit(patch)
                }
            }
        }
    }
}