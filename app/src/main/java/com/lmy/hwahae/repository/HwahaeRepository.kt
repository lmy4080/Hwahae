package com.lmy.hwahae.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lmy.hwahae.datasoruce.HwahaeDataSource
import com.lmy.hwahae.datasoruce.HwahaeDataSourceFactory
import com.lmy.hwahae.datasoruce.api.NetworkState
import com.lmy.hwahae.datasoruce.model.IndexViewProduct
import com.lmy.hwahae.ui.status.UiStatus
import java.util.concurrent.Executors

class HwahaeRepository {

    var mProductList: LiveData<PagedList<IndexViewProduct>>
    private val mProductDataSourceFactory = HwahaeDataSourceFactory()

    /**
     * Create live-data for product list from HwahaeDataSourceFactory
     */
    init {
        val executor = Executors.newFixedThreadPool(5)

        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setPrefetchDistance(2)
            .setEnablePlaceholders(false)
            .build()

        mProductList = LivePagedListBuilder<Int, IndexViewProduct>(mProductDataSourceFactory , config)
            .setFetchExecutor(executor)
            .build()
    }

    /**
     * Set the typed keyword by user for searching
     */
    fun setSearchKeyword(searchQuery: String) {
        UiStatus.currentSearchKeyword = searchQuery
        fetchProductList()
    }

    /**
     * Set the selected skin-type by user for searching
     */
    fun setSkinType(skinType: String) {
        UiStatus.currentSkinType = skinType
        UiStatus.currentSearchKeyword = null
        fetchProductList()
    }

    /**
     * Return live-data holding the state of network
     */
    fun getState(): LiveData<NetworkState> = Transformations.switchMap(mProductDataSourceFactory.mProductListLiveData, HwahaeDataSource::mState)


    /**
     * Fetch new data
     */
    private fun fetchProductList() { mProductList.value?.dataSource?.invalidate() }
}