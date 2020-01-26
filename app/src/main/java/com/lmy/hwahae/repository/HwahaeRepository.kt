package com.lmy.hwahae.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lmy.hwahae.datasoruce.remote.HwahaeDataSourceFactory
import com.lmy.hwahae.datasoruce.remote.api.HwahaeWebService
import com.lmy.hwahae.datasoruce.remote.model.DetailViewItem
import com.lmy.hwahae.datasoruce.remote.model.IndexViewItem
import com.lmy.hwahae.datasoruce.remote.status.NetworkStatus
import com.lmy.hwahae.ui.status.DetailViewStatus
import com.lmy.hwahae.ui.status.IndexViewStatus
import kotlinx.coroutines.*
import java.util.concurrent.Executors

class HwahaeRepository {

    private var mProductList: LiveData<PagedList<IndexViewItem>>
    private var mIsUpdatedProductDetail = MutableLiveData<Boolean>()
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

        mProductList = LivePagedListBuilder<Int, IndexViewItem>(mProductDataSourceFactory , config)
            .setFetchExecutor(executor)
            .build()
    }

    /**
     * Set the typed keyword for searching
     */
    fun setSearchKeyword(searchKeyword: String) {
        IndexViewStatus.currentSearchKeyword = searchKeyword
        fetchProductList()
    }

    /**
     * Set the selected skin-type for searching
     */
    fun setSkinType(skinType: String) {
        IndexViewStatus.currentSkinType = skinType
        IndexViewStatus.currentSearchKeyword = null
        fetchProductList()
    }

    /**
     * Return live-data for product list from HwahaeRepository
     */
    fun getProductList(): LiveData<PagedList<IndexViewItem>> = mProductList

    /**
     * Return live-data holding the state of network
     */
    fun getState(): LiveData<NetworkStatus.State> = NetworkStatus.mState

    /**
     * Return live-data holding the update state of product detail
     */
    fun getIsUpdatedProductDetail(): MutableLiveData<Boolean> = mIsUpdatedProductDetail

    /**
     * Set live-data holding the update state of product detail with flags
     */
    fun setIsUpdatedProductDetail(flag: Boolean) {
        mIsUpdatedProductDetail.postValue(flag)
    }

    /**
     * Return the detail of product
     */
    fun fetchProductDetail(productId: Int?) {

        NetworkStatus.updateNetworkState(NetworkStatus.State.LOADING)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                HwahaeWebService.service.getProductDetail(productId).apply {
                    withContext(Dispatchers.Main) {
                        NetworkStatus.updateNetworkState(NetworkStatus.State.DONE)
                        saveProductDetail(this@apply.body)
                    }
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                NetworkStatus.updateNetworkState(NetworkStatus.State.FAILED)
            }
        }
    }

    /**
     *  Save the detail of product into singleton object
     */
    private fun saveProductDetail(productDetail: DetailViewItem) {

        if(DetailViewStatus.detailViewItem.id != productDetail.id)
            DetailViewStatus.currentPositionY = 0

        DetailViewStatus.detailViewItem = productDetail
        mIsUpdatedProductDetail.postValue(true)
    }

    /**
     * Fetch new data
     */
    private fun fetchProductList() { mProductList.value?.dataSource?.invalidate() }
}


