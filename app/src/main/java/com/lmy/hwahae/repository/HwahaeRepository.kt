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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class HwahaeRepository {

    var mProductList: LiveData<PagedList<IndexViewItem>>
    var mIsUpdatedProductDetail = MutableLiveData<Boolean>()
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
     * Return the detail of product
     */
    fun fetchProductDetail(productId: Int?) {

        NetworkStatus.updateNetworkState(NetworkStatus.State.LOADING)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                HwahaeWebService.service.getProductDetail(productId).apply {
                    withContext(Dispatchers.Main) {
                        saveProductDetail(this@apply?.body)
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


