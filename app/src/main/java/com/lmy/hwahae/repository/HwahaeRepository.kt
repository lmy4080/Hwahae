package com.lmy.hwahae.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lmy.hwahae.datasoruce.HwahaeDataSourceFactory
import com.lmy.hwahae.datasoruce.api.HwahaeWebService
import com.lmy.hwahae.datasoruce.model.DetailViewProduct
import com.lmy.hwahae.datasoruce.model.IndexViewProduct
import com.lmy.hwahae.datasoruce.status.NetworkStatus
import com.lmy.hwahae.ui.status.IndexViewStatus
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
        IndexViewStatus.currentSearchKeyword = searchKeyword
        fetchProductList()
    }

    /**
     * Set the selected skin-type by user for searching
     */
    fun setSkinType(skinType: String) {
        IndexViewStatus.currentSkinType = skinType
        IndexViewStatus.currentSearchKeyword = null
        fetchProductList()
    }

    /**
     * Return live-data holding the state of network
     */
    fun getState(): LiveData<NetworkStatus.State> = NetworkStatus.mState
    //Transformations.switchMap(mProductDataSourceFactory.mProductListLiveData, HwahaeDataSource::mState)

    /**
     * Return live-data holding the detail of product
     */
    fun getProductDetail(): MutableLiveData<DetailViewProduct> = mProductDetail

    /**
     * Update the detail of product
     */
    fun updateProductDetail(productId: Int?) {

        NetworkStatus.updateNetworkState(NetworkStatus.State.LOADING)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                HwahaeWebService.service.getProductDetail(productId).apply {
                    withContext(Dispatchers.Main) {
                        mProductDetail.setValue(this@apply?.body)
                        NetworkStatus.updateNetworkState(NetworkStatus.State.DONE)
                    }
                }
            }
            catch(throwable: Throwable) {
                throwable.printStackTrace()
                NetworkStatus.updateNetworkState(NetworkStatus.State.FAILED)
            }
        }
    }

    /**
     * Fetch new data
     */
    private fun fetchProductList() { mProductList.value?.dataSource?.invalidate() }
}
