package com.lmy.hwahae.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lmy.hwahae.datasoruce.HwahaeDataSource
import com.lmy.hwahae.datasoruce.HwahaeDataSourceFactory
import com.lmy.hwahae.datasoruce.api.HwahaeWebService
import com.lmy.hwahae.datasoruce.api.NetworkState
import com.lmy.hwahae.datasoruce.model.DetailViewProduct
import com.lmy.hwahae.datasoruce.model.IndexViewProduct
import com.lmy.hwahae.ui.status.UiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class HwahaeRepository {

    var mProductList: LiveData<PagedList<IndexViewProduct>>
    var mProductDetail = MutableLiveData<DetailViewProduct>()
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
    fun setSearchKeyword(searchKeyword: String) {
        UiStatus.currentSearchKeyword = searchKeyword
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
     * Return live-data holding the detail of product
     */
    fun getProductDetail(): MutableLiveData<DetailViewProduct> = mProductDetail

    /**
     * Update the detail of product
     */
    fun updateProductDetail(productId: Int?) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                HwahaeWebService.service.getProductDetail(productId).apply {
                    withContext(Dispatchers.Main) {
                        mProductDetail.setValue(this@apply?.body)
                    }
                }
            }
            catch(throwable: Throwable) {
                throwable.printStackTrace()
            }
        }
    }

    /**
     * Fetch new data
     */
    private fun fetchProductList() { mProductList.value?.dataSource?.invalidate() }
}
